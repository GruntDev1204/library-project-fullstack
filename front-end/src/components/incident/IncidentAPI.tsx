import { useRef , useState } from "react"
import Notification from "../notification/Notification"

const IncidentAPI = () => {
  const message: string = "API server is disconnected or is under maintenance!"
  const type: string = "warning"
  const alert = useRef<any>(null)

  const [effect , setEffect] =  useState<string>("")

  const turnOnEffect = () =>{
    setEffect("effect-fan")
  }

  const call = () => {
    alert.current.showAlert(type, message)
  }

  return (
    <div className="container mt-5">
      <div className={`alert alert-notice text-center ${effect}`}>
        <h3 className="medium">
          API server is disconnected or is under maintenance!
          <i
            className="fa-solid fa-plug-circle-xmark warning ml-2"
            onClick={call}
          ></i>
        </h3>
        <h3 className="mt-3 small">
          Đừng ấn vào cái nút này! <i className="fa-solid fa-face-sad-cry" onClick={turnOnEffect}></i>
        </h3>
      </div>
      <Notification ref={alert} />
    </div>
  )
}

export default IncidentAPI
