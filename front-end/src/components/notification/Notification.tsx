import { useImperativeHandle, forwardRef, useState } from "react"
import { NotificationProps } from "../../assets/type/interface"

export interface NotificationRef {
  showAlert: (type: string, message: string) => void
}

const Notification = forwardRef<NotificationRef, NotificationProps>(
  (props = { type: "info", message: "" }, ref) => {
    const [show, setShow] = useState(false)
    const [localType, setLocalType] = useState(props.type)
    const [localMessage, setLocalMessage] = useState(props.message)

    useImperativeHandle(ref, () => ({
      showAlert: (type: string, message: string) => {
        setLocalType(type)
        setLocalMessage(message)
        setShow(true)

        setTimeout(() => {
          setShow(false)
        }, 3000)
      },
    }))

    const getIconClass = (type: string) => {
      switch (type) {
        case "success":
          return "fa-circle-check"
        case "warning":
          return "fa-circle-exclamation"
        case "danger":
          return "fa-triangle-exclamation"
        case "info":
          return "fa-circle-info"
        default:
          return ""
      }
    }

    return show ? (
      <div className="notifi">
        <div className={`alert alert-${localType}`}>
          <h3 className="text-center mb-3 title">
            <i className={`fa-solid ${getIconClass(localType || "")} mr-2`} />
            Notification
          </h3>
          <h3 className="mt-2 content">{localMessage}</h3>
        </div>
      </div>
    ) : null
  }
)

export default Notification
