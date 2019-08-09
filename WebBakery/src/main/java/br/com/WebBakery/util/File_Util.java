package br.com.WebBakery.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;

import br.com.WebBakery.abstractClass.AbstractArquivo;
import br.com.WebBakery.abstractClass.AbstractArquivoTO;

public class File_Util {

    public final static String PATH_SEPARATOR = "/";
    public final static String DOUBLE_PATH_SEPARATOR = "//";
    public final static String SCORE = ".";

    public static String getExtensao(String fileName) {
        if (fileName == null) {
            return null;
        }

        int si = fileName.lastIndexOf(File_Util.SCORE);
        if (si < 0) {
            return null;
        }

        return fileName.substring(si + 1);
    }

    public static String getPath(String nomeArquivo) {
        return File_Util.SCORE + File_Util.DOUBLE_PATH_SEPARATOR + "session_"
                + Faces_Util.getHTTPSession().getId() + File_Util.DOUBLE_PATH_SEPARATOR
                + nomeArquivo;
    }

    public synchronized static String getPastaTemporaria(String webPath, String sessionId)
            throws Exception {
        File fileSession = new File(webPath, "session_" + sessionId);

        if (!fileSession.exists()) {
            fileSession.mkdir();
        }
        return fileSession.getAbsolutePath();
    }

    public static File criarArquivo(byte[] bytes, String caminho, String extensao)
            throws FileNotFoundException, IOException {
        if (caminho == null || bytes == null || bytes.length == 0) {
            return null;
        }

        Date now = new Date();
        caminho += File.separator + "foto_" + now.getTime() + extensao;

        FileOutputStream fileOutputStream = null;
        File arquivo = new File(caminho);
        try {
            if (!arquivo.exists()) {
                arquivo.createNewFile();
            }
            fileOutputStream = new FileOutputStream(arquivo);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return arquivo;
    }

    public static byte[] lerArquivo(File file) throws IOException {
        int maxBufferSize = 1024;
        FileInputStream fstream = new FileInputStream(file);
        int bytesAvailable = fstream.available();
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            int bytesRead = fstream.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                baos.write(buffer);
                bytesAvailable = fstream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fstream.read(buffer, 0, bufferSize);
            }
            fstream.close();
            return baos.toByteArray();
        } finally {
            try {
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String criarFotoPastaTemporaria(AbstractArquivoTO f) throws Exception {
        ServletContext context = (ServletContext) Faces_Util.getExternalContext().getContext();
        File arquivoFoto = File_Util
                .criarArquivo(f.getBytes(),
                              File_Util.getPastaTemporaria(context.getRealPath("/"),
                                                           Faces_Util.getHTTPSession().getId()),
                              File_Util.SCORE + f.getExtensao());
        return arquivoFoto.getAbsolutePath();
    }

    public static String getNomeArquivo(String fullPath) {
        if (fullPath == null) {
            return null;
        }

        int si = fullPath.lastIndexOf(PATH_SEPARATOR);
        if (si < 0) {
            si = fullPath.lastIndexOf("\\");
        }
        if (si < 0) {
            return null;
        }

        return fullPath.substring(si + 1, fullPath.length());
    }
}
