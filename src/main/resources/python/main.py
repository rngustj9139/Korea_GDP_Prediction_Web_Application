from flask import Flask, request
import datetime
import pandas as pd
import joblib
import sklearn

app = Flask(__name__)

result = ""

@app.route("/tospring", methods=['GET', 'POST'])
def spring1():
    if request.method == 'POST':
        print("POST 요청 도착")
        print(request)
        print(request.is_json)

        contents = request.get_json()
        print("경제활동인구수: ", contents['population'])
        print("설비투자지수: ", contents['investment'])
        population = contents['population']
        investment = contents['investment']

        MLmodel = joblib.load('./elasticnet_model.pkl')
        X = pd.DataFrame({'population': [population], 'investment': [investment]})
        predict_y = MLmodel.predict(X)[0]
        print(predict_y)
        global result
        result = predict_y

        return str(result) # 스프링 서버에 응답을 보낸다.(BufferedReader)
    if request.method == 'GET':
        if result == "":
            return "test" # 화면에 출력됨
        else:
            return str(result) # 화면에 출력됨

if __name__ == '__main__':
    app.run(debug=False, host="127.0.0.1", port=5000)