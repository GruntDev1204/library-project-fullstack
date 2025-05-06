export default function HandleError(
  notificationRef: any,
  codeError: number,
  detailError: Record<string, string> | null | undefined,
  message: string
) {
  if (codeError === 500) {
    notificationRef.showAlert(
      "danger",
      "Server bị sida | Code error: " +
        codeError +
        " | Response error : " +
        message
    )
  } else if (
    codeError === 422 &&
    detailError !== null &&
    detailError !== undefined
  ) {
    notificationRef.showAlert("danger", `${message} | Code error: ${codeError}`)

    Object.entries(detailError).forEach(([field, message]) => {
      notificationRef.showAlert("danger", `${field}: ${message}`)
    })
  } 
  else if (codeError === 401){
    notificationRef.showAlert("danger", "authentication is failed")
  }else {
    notificationRef.showAlert("danger", message + " | Code error: " + codeError)
  }
}
