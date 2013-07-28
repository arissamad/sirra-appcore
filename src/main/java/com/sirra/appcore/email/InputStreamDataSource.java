package com.sirra.appcore.email;

import java.io.*;

import javax.activation.*;

public class InputStreamDataSource implements DataSource {
	 
    private String name;
    private String contentType;
    private ByteArrayOutputStream baos;
    
    InputStreamDataSource(String name, String contentType, InputStream inputStream) throws IOException {
        this.name = name;
        this.contentType = contentType;
        
        baos = new ByteArrayOutputStream();
        
        int read;
        byte[] buff = new byte[256];
        while((read = inputStream.read(buff)) != -1) {
            baos.write(buff, 0, read);
        }
    }
    
    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public String getName() {
        return name;
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Cannot write to this read-only resource");
    }
}
