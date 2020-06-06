package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws Exception{
        //线程池机制
        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);

        while (true)
        {
            final Socket accept = serverSocket.accept();

            executorService.execute(new Runnable() {
                public void run() {
                    handle(accept);
                }
            });
        }
    }

    public static void handle(Socket socket)
    {

        byte[] bytes = new byte[1024];
        try {
            InputStream inputStream = socket.getInputStream();
            while (true)
            {
                int read = inputStream.read(bytes);
                if (read != -1)
                {
                    System.out.println(new String(bytes, 0 ,read));
                }
                else
                {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
