   ftp app uses java commons api........file upload app, ftp app

This example using the Apache Commons FileUpload library to create a simple application for uploading files. The program is divided into two parts, a form using JSP and a servlet for handling the upload process. 

The application consists of:
           An upload form that allows user to pick up a file to upload. (upload.jsp)
           A Java servlet that parses the request and saves the uploaded file as a permanent file on the server.
           A message page that shows successful/error messages...with href redirect to create table servlet
           A create table servlet
           A view table servlet

required jars

After downloading the Common File Upload library, extract the zip file to a desired location, and make sure you got a JAR file called commons-fileupload-1.2.2.jar under lib directory. Likewise, the Commons IO library is a dependency for the Common File Upload library, and make sure you got the JAR file commons-io-2.3.jar after extracting the zip file. These JAR files will be used in the project.

Copy two JAR files commons-fileupload-1.2.2.jar and commons-io-2.3.jar to WebContent\WEB-INF\lib directory under project directory.



File Upload Form (jsp)

The first step is to create the upload form. The form contains two fields for selecting file to be uploaded and a submit button. The form should have an enctype attribute and the value is multipart/form-data. We use a post method and the submit process is handled by the FileUploadDemoServlet as defined in the action attribute.

re: jsp page
method=”post”: when submitting, form’s data is sent to server using HTTP POST method. This is required for uploading file because file’s content can be sent only via POST method.

action=”UploadServlet”: specifies a relative path on the server that handles file upload. In this case, we use the path UploadServlet mapped to a Java servlet that handles file upload..be sure that action tag url matches servlet name
     here we are passing control from jsp to servlet...when user presses submit...upload servlet is executed
       and also a message is returned with link to create table servlet which receives the session object from upload servlet

     we originally set a session object in upload servlet to pass file name to create table servlet


enctype="multipart/form-data": tells the browser that this form may contain upload data so the browser handles accordingly.
-          The input tag (type = “file”) displays a text field and a Browse button which allows the user to select a file from his computer.

-          The input tag (type = “submit”) displays a submit button labeled as “Upload”.



The second step is to create the servlet. The doPost method checks to see if the request contains a multipart content. After that we create a FileItemFactory, in this example we use the DiskFileItemFactory which is the default factory for FileItem. This factory creates an instance of FileItem and stored it either in memory or in a temporary file on disk depending on its content size.
      The DiskFileItemFactory class manages file content either in memory or on disk. We call setSizeThreshold() method to specify the threshold above which files are stored on disk, under the directory specified by the method setRepository(). 

The ServletFileUpload class handles multiple files upload that we’ve specified in the form above sent using the multipart/mixed encoding type. The process of storing the data is determined by the FileItemFactory passed to the ServletFileUpload class.
      The ServletFileUpload is the main class that handles file upload by parsing the request and returning a list of file items. It is also used to configure the maximum size of a single file upload and maximum size of the request, by the methods setFileSizeMax()and setSizeMax() respectively.


next step Creating directory to store upload file   

The next step is to parse the multipart/form-data stream by calling the ServletFileUpload.parseRequest(HttpServletRequest request) method. The parse process return a list of FileItem. After that we iterate on the list and check to see if FileItem representing an uploaded file or a simple form field. If it is represent an uploaded file we write the FileItem content to a file.
       Reading upload data and save to file
And this is the most important code: parsing the request to save upload data to a permanent file on disk:      
The parseRequest() method of ServletFileUpload class returns a list of form items which will be iterated to identify the item (represented by a FileItem  object) which contains file upload data. The method isFormField()of FileItem  class checks if an item is a form field or not. A non-form field item may contain upload data, thus the check:

last..And to save the file on disk, we call the write(File) method of FileItem class.



************
How it works
A file upload request comprises an ordered list of items that are encoded according to RFC 1867, "Form-based File Upload in HTML". FileUpload can parse such a request and provide your application with a list of the individual uploaded items. Each such item implements the FileItem interface, regardless of its underlying implementation.










