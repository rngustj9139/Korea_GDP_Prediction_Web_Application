# Korea-GDP-Prediction-Web
한국 GDP 예측 ML 모델을 웹 인터페이스를 통해 이용할 수 있게 하는 프로젝트 

---------------------------

### 프로젝트 소개
사이킷런을 통해 미래 한국 GDP를 예측하는 AI 모델을 ElasticNet 기법을 이용해 구축하였고, 사용자가 웹 인터페이스를 통해 경제 지표 데이터를 입력하면 예측된 GDP가 출력되도록 하였습니다.

사용자의 form data가 스프링 서버에 도착하면 스프링 서버가 HttpUrlConnection을 이용해 JSON 데이터를 플라스크 서버로 전송하고 플라스크 서버는 미리 저장되어있는 AI모델에 경제지표 데이터를
입력하여 예측을 수행합니다. 예측 값은 다시 스프링 서버로 돌아가 모델에 담겨 뷰로 전달됩니다.

**사용 기술 스택**|               
---|
java|
springboot|
python|
flask|
scikitlearn|
MDbootstrap|

---------------------------

### 시스템 구성도
![시스템구성도2](https://user-images.githubusercontent.com/43543906/157829807-d9760c51-f9a5-4877-93c8-9614aea7d1c0.png)

---------------------------

### 사용된 머신러닝 기법
Ridge regression과 Lasso regression의 장점을 모두 이용할 수 있는 ElasticNet 기법을 이용하였습니다.


Ridge regression과 Lasso regression은 둘 다 Ordinary least squares 기법에 penalty(lambda * the slope^2, lambda * |the slope|)를 부과하여 Training data에 대한 Overfitting을 방지합니다. 


LR은 불필요한 변수들을 제거하여 variance를 낮출 수 있다는 장점이 있고, RR은 대부분의 데이터가 유용할 때 사용하면 모델의 성능이 증가합니다.


하지만 모델 속 변수가 유용할지 불필요할지 미리 알 수 없기 때문에 RR과 LR의 장점을 합친 ElasticNet regerssion을 사용하였습니다.


![엘라스틱넷공식2](https://user-images.githubusercontent.com/43543906/157833422-b90ba901-3d01-4668-be96-3c64d1ed631a.png)




![하이퍼파라미터](https://user-images.githubusercontent.com/43543906/157834921-0bee7930-93fe-4b76-b148-ea491710641a.png)

---------------------------

### 작동 모습
![git1](https://user-images.githubusercontent.com/43543906/157830744-d12569f5-0d9a-4b2b-97ad-5bc23856fb9c.png)
![git2](https://user-images.githubusercontent.com/43543906/157830751-0040e397-d159-4aee-a4ed-799da7295b57.png)
![git3](https://user-images.githubusercontent.com/43543906/157830758-e281d50a-e36a-4630-bb1e-a8d10ce20cca.png)


---------------------------


> 경희대학교 소프트웨어융합학과 구현서 [mnb9139@khu.ac.kr]
