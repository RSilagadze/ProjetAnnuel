package tools ;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class CryptoUtils
{

    private final static String userKeyFilePath = "userKey" ;

    public static String generateKey()
    {
        Random random = new Random() ;

        String generatedString = "" ;

        for(int i = 0 ; i < 20 ; i++)
        {
            int letter = random.nextInt(74) + 48 ;
            generatedString += (char) letter ;
        }

        return generatedString ;
    }

    public static void encryptCBCBytes(ByteArrayInputStream bar, ByteArrayOutputStream bos, byte[] prevBlock, String key)
            throws Exception
    {
        byte[] keyBytes = key.getBytes() ;
        int blockLength = keyBytes.length ;
        byte[] blockBuff = new byte[blockLength] ;

        if(blockLength != prevBlock.length)
            throw new Exception("La clé et le vecteur d'initialisation doivent contenir le même nombre de caractères") ;

        int bRead ;

        while((bRead = bar.read(blockBuff, 0, blockLength)) > 0)
        {
            for(int i = 0 ; i < bRead ; i++)
            {
                int xor = (int)prevBlock[i] ^ (int)blockBuff[i] ^ (int)keyBytes[i] ;
                prevBlock[i] = (byte)xor ;
                bos.write(xor) ;
            }

            encryptCBCBytes(bar, bos, prevBlock, key) ;
        }
    }

    public static void decryptCBCBytes(ByteArrayInputStream bar, ByteArrayOutputStream bos, byte[] prevBlock, String key)
            throws Exception
    {
        byte[] keyBytes = key.getBytes() ;
        int blockLength = keyBytes.length ;
        byte[] blockBuff = new byte[blockLength] ;

        if(blockLength != prevBlock.length)
            throw new Exception("La clé et le vecteur d'initialisation doivent contenir le même nombre de caractères") ;

        int bRead ;

        while((bRead = bar.read(blockBuff, 0, blockLength)) > 0)
        {
            for(int i = 0; i < bRead; i++)
            {
                int xor = (int)blockBuff[i] ^ (int)keyBytes[i] ^ (int)prevBlock[i] ;
                bos.write(xor) ;
            }

            decryptCBCBytes(bar, bos, blockBuff, key) ;
        }
    }

    public static byte[] cryptECBBytes(byte[] bytes, String key)
            throws Exception
    {
        byte[] keyBytes = key.getBytes() ;
        int blockLength = keyBytes.length ;
        byte[] blockBuff = new byte[blockLength] ;

        ByteArrayOutputStream bos = new ByteArrayOutputStream(128) ;

        try(ByteArrayInputStream bar = new ByteArrayInputStream(bytes))
        {
            int bRead ;

            while((bRead = bar.read(blockBuff, 0, blockLength)) > 0)
            {
                for (int i = 0; i < bRead; i++)
                {
                    int xor = (int)blockBuff[i] ^ (int)keyBytes[i] ;
                    bos.write(xor) ;
                }
            }
        }

        byte[] result  = bos.toByteArray() ;
        bos.close() ;

        return result ;
    }

    public static void cryptECBFile(String path, String key)
            throws Exception
    {
        Path file = Paths.get(path) ;

        byte[] cryptResult = cryptECBBytes(Files.readAllBytes(file), key) ;

        Files.write(file, cryptResult) ;
    }

    public static String getKey()
    {
        String key = "" ;
        File file = new File(userKeyFilePath) ;

        if(file.exists())
        {
            try
            {
                Path filePath = Paths.get(userKeyFilePath) ;
                key = new String(Files.readAllBytes(filePath)) ;
            }

            catch (FileNotFoundException e)
            {
                e.printStackTrace() ;
            }

            catch (IOException e)
            {
                e.printStackTrace() ;
            }
        }

        else
        {
            key = setKey() ;
        }

        return key ;
    }

    public static String setKey()
    {
        String key = "" ;
        Path userkeyFile = Paths.get(userKeyFilePath) ;

        try
        {
            key = generateKey() ;
            Files.write(userkeyFile, key.getBytes()) ;
        }

        catch (IOException e)
        {
            e.printStackTrace() ;
        }

        return key ;
    }

}
