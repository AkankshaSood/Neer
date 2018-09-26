package com.google.sample.cloudvision;

import java.security.AccessController;

import javax.mail.Provider;
import javax.net.ssl.SSLContext;

/**
 * Created by hp on 3/27/2018.
 */



public final class JSSEProvider extends java.security.Provider {


        public JSSEProvider() {


            super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");


            AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {


                public Void run() {


                    put("SSLContext.TLS",


                            "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");


                    put("Alg.Alias.SSLContext.TLSv1", "TLS");


                    put("KeyManagerFactory.X509",


                            "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");


                    put("TrustManagerFactory.X509",


                            "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");


                    return null;


                }


            });


        }


    }