import config from './config';

const errTip = {
    10000: '出现了一个错误',
    10001: 'xxxx',
    10002: 'xxxx'
}

class HttpRequest {
    request(params) {
        wx.request({
            url: config.endPoint + params.url,
            method: params.method,
            data: params.data,
            header: {
                'Content-Type': 'application/json',
                'Access-Token': config.accessToken
            },

            success: (res) => {
                let code = res.statusCode;
                if(code.startWith('2')) {
                    params.success(res.data)
                } else {
                    this._showErrMsg(res.data.message)
                    console.log('err', res.data);
                }
            },
            fail: (err) => {
                this._showErrMsg(10000)
            }
        })
    }

    _showErrMsg(errCode) {
        if(!errCode) {
            wx.showToast({
                title: errTip[10000],
                icon: 'none',
                duration: 1500
            })
        }

        wx.showToast({
            title: errTip[errCode],
            icon: 'none',
            duration: 1500
        })
    }
}

export default HttpRequest;