import { useMemo, useRef, useState } from "react"
import { Link } from "react-router-dom"
import baseApi from "../assets/api/baseApi"
import Notification, { NotificationRef } from "./notification/Notification"

const Header = () => {
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null)
  const [profile, setProfile] = useState<any | null>(null)
  const alert = useRef<NotificationRef | null>(null)

  const getProfile = () => {
    baseApi
      .getAccess("customers/auth/profile", alert.current)
      .then((res: any) => {
        setProfile(res.data.data)
      })
  }

  useMemo(() => {
    baseApi
      .getAccess("customers/auth/verify-token", alert.current)
      .then((res: any) => {
        if (res && res.data && res.data.data) {
          setIsAuthenticated(true)
        } else {
          setIsAuthenticated(false)
          alert.current?.showAlert("info", "Hãy đăng nhập để sử dụng tối cmn ưu nhé")
        }
      })

    if (isAuthenticated) {
      getProfile()
    }
  }, [isAuthenticated])

  return (
    <>
      <div className="header">
        <div className="container">
          <div className="header-left">
            <i className="fa-solid fa-building-columns medium mr-2 "></i>{" "}
            <p className="medium">
              {" "}
              <Link to="" className="link">
                Library Project{" "}
              </Link>{" "}
            </p>
          </div>
          {isAuthenticated && profile && profile !== null ? (
            <div className="header-right">
              <p>
                {" "}
                <i className="fa-solid fa-user"></i> {profile.fullName}
              </p>
            </div>
          ) : (
            <div className="header-right">
              <button className="button">
                <Link to="/auth/sign-up">
                  {" "}
                  <i className="fa-solid fa-user-plus"></i> Register{" "}
                </Link>
              </button>
              <button className="button">
                <Link to="/auth/login">
                  {" "}
                  <i className="fa-solid fa-user"></i> Login{" "}
                </Link>
              </button>
            </div>
          )}
        </div>
      </div>
      <Notification ref={alert} />
    </>
  )
}

export default Header
