package com.wakeUpTogetUp.togetUp.api.objectDetection.domain;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Component;

@Component
public class Letterbox {

    private Size newShape = new Size(1280, 1280);
    private double[] color = new double[]{114,114,114};
    private Boolean auto = false;
    private Boolean scaleUp = true;
    private Integer stride = 32;

    private double ratio;
    private double dw;
    private double dh;

    public double getRatio() {
        return ratio;
    }

    public double getDw() {
        return dw;
    }

    public Integer getWidth() {
        return (int) this.newShape.width;
    }

    public Integer getHeight() {
        return (int) this.newShape.height;
    }

    public double getDh() {
        return dh;
    }

    public void setNewShape(Size newShape) {
        this.newShape = newShape;
    }

    public void setStride(Integer stride) {
        this.stride = stride;
    }

    public Mat letterbox(Mat im) { // 단계 제약 조건을 충족하도록 이미지 크기 조정 및 채우기, 매개 변수 기록

        int[] shape = {im.rows(), im.cols()}; // 현재 모양 [height, width]
        // Scale ratio (new / old)
        double r = Math.min(this.newShape.height / shape[0], this.newShape.width / shape[1]);
        if (!this.scaleUp) { // 축소만, 확대는 하지 않음(일단 mAP를 위해서)
            r = Math.min(r, 1.0);
        }
        // Compute padding
        Size newUnpad = new Size(Math.round(shape[1] * r), Math.round(shape[0] * r));
        double dw = this.newShape.width - newUnpad.width, dh = this.newShape.height - newUnpad.height; // wh 填充
        if (this.auto) { // 최소 직사각형
            dw = dw % this.stride;
            dh = dh % this.stride;
        }
        dw /= 2; // 채울 때 양쪽을 절반씩 채워 그림을 중심에 놓으십시오
        dh /= 2;
        if (shape[1] != newUnpad.width || shape[0] != newUnpad.height) { // resize
            Imgproc.resize(im, im, newUnpad, 0, 0, Imgproc.INTER_LINEAR);
        }
        int top = (int) Math.round(dh - 0.1), bottom = (int) Math.round(dh + 0.1);
        int left = (int) Math.round(dw - 0.1), right = (int) Math.round(dw + 0.1);
        // 그림을 정사각형으로 채우기
        Core.copyMakeBorder(im, im, top, bottom, left, right, Core.BORDER_CONSTANT, new org.opencv.core.Scalar(this.color));
        this.ratio = r;
        this.dh = dh;
        this.dw = dw;
        return im;
    }
}