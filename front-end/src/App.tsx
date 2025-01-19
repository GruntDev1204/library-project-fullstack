import { useEffect, useState } from "react"
import baseApi from "./assets/api/baseApi"
import IncidentAPI from "./components/incident/IncidentAPI"
import Loading from "./components/Loading"
import Header from "./components/Header"
import { Routes, Route, useLocation } from "react-router-dom"
import Body from "./components/Body"
import Login from "./components/auth/Login"
import Home from "./components/body/Home"
import SignUp from "./components/auth/Signup"
import Footer from "./components/Footer"
import Auth from "./components/Auth"
import Detail from "./components/body/Detail"

function App() {
  const [isCheckApi, setIsCheckApi] = useState<boolean>(false)
  const [loading, setIsLoading] = useState<boolean>(true)

  const startUp = () => {
    setTimeout(() => {
      setIsLoading(false)
    }, 3000)
  }

  useEffect(() => {
    startUp()
    checkApi()
  }, [])

  const location = useLocation()
  const isAuthRoute = location.pathname.startsWith("/auth")


  const checkApi = async () => {
    try {
      const check = await baseApi.check()
      setIsCheckApi(check)
    } catch (error) {
      console.error("Failed to check API connection:", error)
      setIsCheckApi(false)
    }
  }

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
          {!isAuthRoute && <Footer />}{" "}
        </>
      )}
    </>
  )
}

export default App
