import { Link, useNavigate } from "react-router-dom"
import Cookies from "js-cookie"
import baseApi from "../../assets/api/baseApi"
import Notification, { NotificationRef } from "../notification/Notification"
import { useRef, useState } from "react"

export default function Login() {
  const [userName, setUserName] = useState<string>("")
  const [password, setPassword] = useState<string>("")
  const alert = useRef<NotificationRef | null>(null)

  const tab = useNavigate()
  const login = async () => {
    const res: any = await baseApi.post(
      "customers/auth/login",
      { userName, password },
      alert.current
    )
    if (!userName || !password) {
      alert.current?.showAlert("warning", "thông tin login ko đủ")
      return
    }
    if (res.data) {
      if (res.data.code === 20000) {
        alert.current?.showAlert("success", res.data.message)
        Cookies.set("access-auth-token", res.data.data.accessToken)
        setTimeout(() => {
          tab("/")
        }, 3000)
      } else {
        alert.current?.showAlert("error", "login thất bại cmnr thằng lol")
      }
    } else {
      alert.current?.showAlert("error", "server sida")
    }
  }

  return (
    <>
      <div className="alert alert-form form-login">
        <div className="row mt-4 mb-2">
          <div className="col medium">
            <h3 className="text-center mb-3 title">
              <i className="fa-solid fa-user mr-2" />
              Login
            </h3>
          </div>
        </div>

        <div className="row mt-5 mb-5">
          <div className="col">
            <div className="form-group">
              <label className="mb-2">user name </label>
              <input
                type="text"
                className="form-control mb-4"
                placeholder="user name"
                onChange={(e) => setUserName(e.target.value)}
                value={userName}
              />
            </div>
            <div className="form-group mt-5  ">
              <label className="mb-2">Password </label>
              <input
                type="password"
                className="form-control mb-2"
                placeholder="Password"
                onChange={(e) => setPassword(e.target.value)}
                value={password}
              />
            </div>

            <button
              type="submit"
              className="button mt-5 small"
              onClick={() => login()}
            >
              Login
            </button>
            <button type="submit" className="button mt-5 small ml-3">
              <Link to="/auth/sign-up">Register?</Link>
            </button>
          </div>
        </div>
      </div>
      <Notification ref={alert} />
    </>
  )
}
