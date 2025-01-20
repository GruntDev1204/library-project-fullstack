import { useRef, useState } from "react"
import { Link } from "react-router-dom"
import { userService } from "../../assets/type/interface"
import Notification, { NotificationRef } from "../notification/Notification"
import baseApi from "../../assets/api/baseApi"

export default function SignUp() {
  const alert = useRef<NotificationRef | null>(null)

  const df: userService = {
    email: "",
    phoneNumber: "",
    fullName: "",
    userName: "",
    password: "",
  }
  const [continute, setContinute] = useState<boolean>(false)
  const [newUser, setNewUser] = useState<userService>(df)
  const [retypePassword, setRetypePassword] = useState<string>("")

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNewUser({ ...newUser, [e.target.name]: e.target.value })
  }

  const getContinute = () => {
    setContinute(!continute)
  }

  const registerUser = () => {
    if (
      !newUser.email ||
      !newUser.phoneNumber ||
      !newUser.fullName ||
      !newUser.userName ||
      !newUser.password
    ) {
      alert.current?.showAlert("warning", "Please fill in all the fields.")
      return
    }
    if (newUser.password !== retypePassword) {
      alert.current?.showAlert(
        "danger",
        "Password and retype password are not the same."
      )
      return
    }

    baseApi
      .post("customers/register", newUser, alert.current)
      .then((res: any) => {
        alert.current?.showAlert("success", res.data.message)
        setContinute(false)
        setNewUser(df)
        setTimeout(() => {
          window.location.href = "/auth/login"
        }, 3000)
      })
  }
  return (
    <>
      <Notification ref={alert} />
      <div className="alert alert-form form-login">
        <div className="row mt-4 mb-2">
          <div className="col medium">
            <h3 className="text-center mb-3 title">
              <i className="fa-solid fa-user-plus"></i> Register
            </h3>
          </div>
        </div>

        <div className="row mt-5 mb-5">
          <div className="col">
            <div className="form-group">
              <label className="mb-2">
                {" "}
                <i className="fa-solid fa-envelope"></i> Email address
              </label>
              <input
                type="email"
                className="form-control mb-4"
                name="email"
                placeholder="Enter email"
                onChange={handleChange}
                value={newUser.email}
              />
            </div>

            <div className="form-group mt-5">
              <label className="mb-2">
                <i className="fa-solid fa-phone"></i> Phone Number
              </label>
              <input
                type="text"
                className="form-control mb-4"
                name="phoneNumber"
                placeholder="Enter phone number - you can skip this step"
                onChange={handleChange}
                value={newUser.phoneNumber}
              />
            </div>

            <div className="form-group mt-5">
              <label className="mb-2">
                <i className="fa-solid fa-circle-user"></i> Full Name
              </label>
              <input
                type="text"
                className="form-control mb-4"
                name="fullName"
                placeholder="Enter full name"
                onChange={handleChange}
                value={newUser.fullName}
              />
            </div>

            <small className="form-text text-muted mt-5 text-center flex-center mb-5">
              We will use the information below to create an account for you!{" "}
              <i
                className="fa-solid fa-circle-chevron-right medium go-on"
                title="continute"
                onClick={getContinute}
              ></i>
            </small>

            {continute ? (
              <>
                <div className="form-group mt-2 ">
                  <label className="mb-2">
                    <i className="fa-solid fa-user"></i> User Name
                  </label>
                  <input
                    type="text"
                    className="form-control mb-4"
                    name="userName"
                    onChange={handleChange}
                    value={newUser.userName}
                    placeholder="Enter user name for account"
                  />
                </div>

                <div className="form-group mt-5 ">
                  <label className="mb-2">
                    {" "}
                    <i className="fa-solid fa-lock"></i> Password{" "}
                  </label>
                  <input
                    type="password"
                    className="form-control mb-2"
                    name="password"
                    placeholder="Enter password"
                    onChange={handleChange}
                    value={newUser.password}
                  />
                </div>

                <div className="form-group mt-5 mb-5">
                  <label className="mb-2">
                    <i className="fa-solid fa-lock"></i> ReType Password{" "}
                  </label>
                  <input
                    type="password"
                    placeholder="ReType password"
                    className="form-control mb-2"
                    onChange={(e) => setRetypePassword(e.target.value)}
                    value={retypePassword}
                  />
                </div>
              </>
            ) : (
              ""
            )}

            {continute && (
              <button
                type="submit"
                className="button mt-5 small"
                onClick={() => {
                  registerUser()
                }}
              >
                Submit
              </button>
            )}

            <button type="submit" className="button mt-5 small ml-3">
              <Link to="/auth/login">Login?</Link>
            </button>
          </div>
        </div>
      </div>
    </>
  )
}
