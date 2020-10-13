from fastapi import FastAPI

app = FastAPI()


@app.get("/")
def hello():
    return {"msg": "Hello, this is Kininaru"}


@app.get("/dev-test/login/{uid}")
def login(uid: str):
    if len(uid) > 30:
        return {'step': -2}
    stat = -1
    with open('dev-test/usr.conf', mode='r') as file:
        data = file.readline()
        while data:
            name = data.split(',')[0]
            if name == uid:
                stat = int(data.split(',')[1])
                break
            data = file.readline()
    if stat == -1:
        with open('dev-test/usr.conf', mode='a') as file:
            file.write('\n' + uid + ',1')
        return {'step': 1}
    else:
        return {'step': stat}
