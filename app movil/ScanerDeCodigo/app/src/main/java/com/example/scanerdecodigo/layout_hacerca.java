package com.example.scanerdecodigo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class layout_hacerca extends Fragment {

    TextView tv_descarga,tv_video,tv_pagina_web,tv_mumero;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_layout_hacerca, container, false);

        tv_descarga=(TextView)vista.findViewById(R.id.tv_descarga_frag);
        tv_video=(TextView)vista.findViewById(R.id.tv_vervideo_frag);
        tv_pagina_web=(TextView)vista.findViewById(R.id.tv_pagina_Web);
        tv_mumero=(TextView)vista.findViewById(R.id.tv_mumero);


        tv_descarga.setMovementMethod(LinkMovementMethod.getInstance());
        tv_video.setMovementMethod(LinkMovementMethod.getInstance());
        tv_pagina_web.setMovementMethod(LinkMovementMethod.getInstance());
        tv_mumero.setMovementMethod(LinkMovementMethod.getInstance());

        return vista;
    }
}