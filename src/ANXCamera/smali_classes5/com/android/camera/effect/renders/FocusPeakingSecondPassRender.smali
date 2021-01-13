.class Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;
.super Lcom/android/camera/effect/renders/ConvolutionEffectRender;
.source "FocusPeakingRender.java"


# static fields
.field private static final FRAG:Ljava/lang/String; = "precision mediump float; \n//uniform vec2 uStep; \n//uniform vec3 uPeakColor; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nvoid main() { \n    vec3 sum = texture2D(sTexture, vTexCoord).rgb; \n    if (any(greaterThan(sum, vec3(0.0, 0.0, 0.0)))) { \n        gl_FragColor.rgb = vec3(sum); \n        gl_FragColor.a = 1.0; \n    } else { \n        gl_FragColor.rgb = vec3(1.0, 0.0, 0.0); \n        gl_FragColor.a = 0.0; \n    } \n}"


# instance fields
.field private mPeakColor:I

.field private mUniformPeakColorH:I


# direct methods
.method public constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;)V
    .locals 0

    invoke-direct {p0, p1}, Lcom/android/camera/effect/renders/ConvolutionEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;)V

    const p1, 0xf9310f

    iput p1, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    return-void
.end method

.method public constructor <init>(Lcom/android/gallery3d/ui/GLCanvas;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Lcom/android/camera/effect/renders/ConvolutionEffectRender;-><init>(Lcom/android/gallery3d/ui/GLCanvas;I)V

    const p1, 0xf9310f

    iput p1, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    return-void
.end method


# virtual methods
.method public getFragShaderString()Ljava/lang/String;
    .locals 0

    const-string p0, "precision mediump float; \n//uniform vec2 uStep; \n//uniform vec3 uPeakColor; \nuniform sampler2D sTexture; \nvarying vec2 vTexCoord; \nvoid main() { \n    vec3 sum = texture2D(sTexture, vTexCoord).rgb; \n    if (any(greaterThan(sum, vec3(0.0, 0.0, 0.0)))) { \n        gl_FragColor.rgb = vec3(sum); \n        gl_FragColor.a = 1.0; \n    } else { \n        gl_FragColor.rgb = vec3(1.0, 0.0, 0.0); \n        gl_FragColor.a = 0.0; \n    } \n}"

    return-object p0
.end method

.method protected initShader()V
    .locals 2

    invoke-super {p0}, Lcom/android/camera/effect/renders/ConvolutionEffectRender;->initShader()V

    iget v0, p0, Lcom/android/camera/effect/renders/ShaderRender;->mProgram:I

    const-string v1, "uPeakColor"

    invoke-static {v0, v1}, Landroid/opengl/GLES20;->glGetUniformLocation(ILjava/lang/String;)I

    move-result v0

    iput v0, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mUniformPeakColorH:I

    return-void
.end method

.method protected initShaderValue(Z)V
    .locals 3

    invoke-super {p0, p1}, Lcom/android/camera/effect/renders/ConvolutionEffectRender;->initShaderValue(Z)V

    iget p1, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mUniformPeakColorH:I

    iget v0, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    invoke-static {v0}, Landroid/graphics/Color;->red(I)I

    move-result v0

    int-to-float v0, v0

    const/high16 v1, 0x437f0000    # 255.0f

    div-float/2addr v0, v1

    iget v2, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    invoke-static {v2}, Landroid/graphics/Color;->green(I)I

    move-result v2

    int-to-float v2, v2

    div-float/2addr v2, v1

    iget p0, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    invoke-static {p0}, Landroid/graphics/Color;->blue(I)I

    move-result p0

    int-to-float p0, p0

    div-float/2addr p0, v1

    invoke-static {p1, v0, v2, p0}, Landroid/opengl/GLES20;->glUniform3f(IFFF)V

    return-void
.end method

.method protected setBlendEnabled(Z)V
    .locals 0

    const/16 p0, 0xbe2

    invoke-static {p0}, Landroid/opengl/GLES20;->glEnable(I)V

    return-void
.end method

.method public setPeakColor(I)V
    .locals 0

    iput p1, p0, Lcom/android/camera/effect/renders/FocusPeakingSecondPassRender;->mPeakColor:I

    return-void
.end method
