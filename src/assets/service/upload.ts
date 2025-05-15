import { getDownloadURL, ref, uploadBytes } from "firebase/storage"
import { storage } from "../../config/firebase"

const urlStorage: string = `lib_management`

export const uploadAvatar = async (file: File): Promise<string> => {
  try {
    const storageRef = ref(storage, `${urlStorage}/${file.name}`)
    console.log("Storage reference created:", storageRef)

    const uploadResult = await uploadBytes(storageRef, file)
    console.log("Upload result:", uploadResult)

    const downloadURL = await getDownloadURL(storageRef)
    console.log("Download URL:", downloadURL)

    alert("tải ảnh lên thành công! ")

    return downloadURL
  } catch (error) {
    alert("Lỗi khi tải ảnh lên Firebase!")
    console.error(error)
    return ""
  }
}
