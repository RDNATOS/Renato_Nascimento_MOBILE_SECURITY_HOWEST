console.log("Overwrite Login Script loaded succesfully");

Java.perform(function () {
    const LoginPageClass = Java.use("com.example.renato_nascimento_mobile_security_project.LoginPage");

    // Hook the checkPassword method
    LoginPageClass.checkPassword.overload('java.lang.String', 'java.lang.String').implementation = function (userPassword, password) {
        // Your hooking logic here
        console.log('checkPassword method hooked!');
        
        // Call the original method and return its result
        return true;
    };
});