Shader "Hidden/ChannelMixer" {

	Properties {
		_MainTex("Base (RGB)", 2D) = "white" {}
	}

	SubShader {
		Pass {
			CGPROGRAM

			#pragma vertex vert_img
			#pragma fragment frag

			#include "UnityCG.cginc"

			uniform sampler2D _MainTex;
			uniform fixed4x4 _mat;

			fixed4 frag(v2f_img i) : COLOR {
				fixed4 c = tex2D(_MainTex, i.uv);
				return mul(_mat, c);
			}
			ENDCG
		}
	}
}