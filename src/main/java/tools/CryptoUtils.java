package tools ;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class CryptoUtils
{

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

}
