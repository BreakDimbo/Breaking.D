package com.limbo.shopping.util.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

public class FileUpload extends HttpServlet {
    //BLOB

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    private ServletConfig config = null;


    private File tempPath = new File("/Users/Break.D/Desktop/temp"); // 用于存放临时文件的目录

    public void destroy() {
        config = null;
        super.destroy();
    }


    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        int id = -1;

        String uploadPath = config.getInitParameter("uploadPath"); // 用于存放上传文件的目录

        res.setContentType("text/html; charset=utf-8");
        PrintWriter out = res.getWriter();
        System.out.println(req.getContentLength());
        System.out.println(req.getContentType());
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(100000000);
        // the location for saving data that is larger than getSizeThreshold()
        factory.setRepository(tempPath);

        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum size before a FileUploadException will be thrown
        upload.setSizeMax(100000000);
        try {
            List fileItems = upload.parseRequest(req);
            // assume we know there are two files. The first file is a small
            // text file, the second is unknown and is written to a file on
            // the server
            Iterator iter = fileItems.iterator();


            // 过滤掉的文件类型
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    if (item.getFieldName().equals("id")) {
                        id = Integer.parseInt(item.getString());
                    }
                }
                // 忽略其他不是文件域的所有表单信息
                if (!item.isFormField()) {
                    String name = item.getName();
                    System.out.println(name);
                    long size = item.getSize();
                    if ((name == null || name.equals("")) && size == 0)
                        continue;
                    try {

                        // 保存上传的文件到指定的目录

                        // 在下文中上传文件至数据库时，将对这里改写
                        item.write(new File(uploadPath + id + ".jpg"));

                        out.print(name + "&nbsp;&nbsp;" + size + "<br>");
                    } catch (Exception e) {
                        out.println(e);
                    }
                }
            }
        } catch (FileUploadException e) {
            out.println(e);
        }

    }


}
