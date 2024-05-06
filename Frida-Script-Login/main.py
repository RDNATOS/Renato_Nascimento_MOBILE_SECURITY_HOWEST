import time
import frida

device = frida.get_usb_device()
pid = device.spawn(["com.example.renato_nascimento_mobile_security_project"]) #spawns the app
device.resume(pid)
time.sleep(1)

session = device.attach(pid) #creates a session
with open("overwriteLogin.js") as f:
    script = session.create_script(f.read()) #makes a js script
script.load()

input()