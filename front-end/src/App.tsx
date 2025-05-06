import { useMemo, useRef, useState } from "react"
import { Routes, Route, useLocation, useNavigate } from "react-router-dom"
import baseApi from "./assets/api/baseApi"
import IncidentAPI from "./components/incident/IncidentAPI"
import Loading from "./components/Loading"
import Header from "./components/Header"
import Body from "./components/Body"
import Login from "./components/auth/Login"
import Home from "./components/body/Home"
import SignUp from "./components/auth/Signup"
import Footer from "./components/Footer"
import Auth from "./components/Auth"
import Detail from "./components/body/Detail"
import Notification, { NotificationRef } from "./components/notification/Notification"

function App() {
  const [isCheckApi, setIsCheckApi] = useState<boolean>(false)
  const [loading, setIsLoading] = useState<boolean>(true)
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(false)
  const navigate = useNavigate()
  const location = useLocation()
  const isAuthRoute = location.pathname.startsWith("/auth")
  const alert = useRef<NotificationRef | null>(null)

  const checkApi = async () => {
    try {
      const check = await baseApi.check()
      setIsCheckApi(check)
    } catch (error) {
      setIsCheckApi(false)
    }
  }
  const verifyToken = async () => {
    try {
      const res: any = await baseApi.getAccess(
        "customers/auth/verify-token",
        alert.current
      )
      if (res.data.data) {
        setIsAuthenticated(true)
      } else {
        setIsAuthenticated(false)
      }
    } catch (error) {
      setIsAuthenticated(false)
    }
  }

  const startUp = () => {
    setTimeout(() => {
      setIsLoading(false)
    }, 3000)
  }

  useMemo(() => {
    startUp()
    checkApi()
    verifyToken()
  }, [])

  useMemo(() => {
    if (isAuthenticated && isAuthRoute) {
      navigate("/")
      alert.current?.showAlert("info", "you are authenticated")
    }
  }, [isAuthenticated, isAuthRoute, navigate])

  return (
    <>
      {loading ? (
        <Loading />
      ) : !isCheckApi ? (
        <IncidentAPI />
      ) : (
        <>
          <Header />
          <Routes>
            <Route path="/auth" element={<Auth />}>
              <Route path="login" element={<Login />} />
              <Route path="sign-up" element={<SignUp />} />
            </Route>
            <Route path="/" element={<Body />}>
              <Route path="" element={<Home />} />
              <Route path="/detail/:id/:request" element={<Detail />} />
            </Route>
          </Routes>
          {!isAuthRoute && <Footer />}
        </>
      )}
      <Notification ref={alert} />
    </>
  )
}

export default App
