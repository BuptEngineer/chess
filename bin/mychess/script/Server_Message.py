#!/usr/bin/python3

import socket,threading,time

class Server_Message(object):
    def __init__(self,ip,port):
        self._ip=ip
        self._port=port
        self.ls=[] #保存连接的客户端

    def service(self):
        s=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        s.bind((self._ip,self._port))
        s.listen(10)
        while True:
               sock,addr=s.accept()
               self.ls.append(sock)
               t=threading.Thread(target=Server_Message.process,args=(self,sock,addr))
               t.start()
            
    def process(self,sock,addr):
               while True:
                   try:
                       data=sock.recv(1024)
                       #发给其余客户端
                       for cs in self.ls:
                           if cs==sock:
                                    continue
                           cs.send(data) #转发消息
                   except socket.error:
                         sock.close()
                         return 1

if __name__=='__main__':
    print('open')
    Server_Message('127.0.0.1',12456).service()
                       
               
               
