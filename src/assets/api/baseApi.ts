import axios from "axios"
import HandleError from "./handleError"
import Cookies from "js-cookie"

const baseRoute = "http://localhost:8080/"
const apiCheck = "http://localhost:8080/api"

const check = (error: any) => {
  const status = error.response ? error.response.status : null
  const message = error.response
    ? error.response.data.message
    : "Không thể kết nối đến server."
  return { status, message }
}

const accessToken = Cookies.get("access-auth-token")
  ? Cookies.get("access-auth-token")
  : null

const baseApi = {
  async check() {
    try {
      const res = await axios.get(apiCheck)
      return res.data.data
    } catch (error) {
      return false
    }
  },

  getAccess(url: string, alert: any): any {
    return axios
      .get(baseRoute + url, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .catch((err) => {
        const { status, message } = check(err)
        HandleError(alert, status, null, message)
      })
  },

  async postAccess(url: string, data: any, alert: any) {
    return axios
      .post(baseRoute + url, data, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .catch((err) => {
        const { status, message } = check(err)
        HandleError(alert, status, err.response.data.detail, message)
      })
  },

  get(url: string, alert: any): any {
    return axios.get(baseRoute + url).catch((err) => {
      const { status, message } = check(err)
      HandleError(alert, status, null, message)
    })
  },

  async getByAttribute(url: string, params: Record<string, any>, alert: any) {
    return axios.get(baseRoute + url, { params }).catch((err) => {
      const { status, message } = check(err)
      HandleError(alert, status, null, message)
    })
  },

  async post(url: string, data: any, alert: any) {
    return axios.post(baseRoute + url, data).catch((err) => {
      const { status, message } = check(err)
      HandleError(alert, status, err.response.data.detail, message)
    })
  },

  async delete(url: string, alert: any) {
    return axios.delete(baseRoute + url).catch((err) => {
      const { status, message } = check(err)
      HandleError(alert, status, null, message)
    })
  },

  async put(url: string, data: any, alert: any) {
    return axios.put(baseRoute + url, data).catch((err) => {
      const { status, message } = check(err)
      HandleError(alert, status, err.response.data.detail, message)
    })
  },
}

export default baseApi
