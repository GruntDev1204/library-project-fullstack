import axios from "axios"

import HandleError from "./handleError"

const baseRoute = "http://localhost:8080/"

export default {
  async check() {
    try {
      const res = await axios.get(baseRoute)
      return res.data.data
    } catch (error) {
      console.error("Error while checking API:", error)
      return false
    }
  },

  get(url: string, alert: any): any {
    return axios.get(baseRoute + url).catch((err) => {
      HandleError(alert, err.response.status, null, err.response.data.message)
    })
  },

  async getByAttribute(url: string, params: Record<string, any>, alert: any) {
    return axios.get(baseRoute + url, { params }).catch((err) => {
      HandleError(alert, err.response.status, null, err.response.data.message)
    })
  },

  async post(url: string, data: any, alert: any) {
    return axios.post(baseRoute + url, data).catch((err) => {
      HandleError(
        alert,
        err.response.status,
        err.response.data.detail,
        err.response.data.message
      )
    })
  },

  async delete(url: string, alert: any) {
    return axios.delete(baseRoute + url).catch((err) => {
      HandleError(alert, err.response.status, null, err.response.data.message)
    })
  },

  async put(url: string, data: any, alert: any) {
    return axios.put(baseRoute + url, data).catch((err) => {
      HandleError(
        alert,
        err.response.status,
        err.response.data.detail,
        err.response.data.message
      )
    })
  },
}
