import { useParams, useNavigate } from "react-router-dom"
import {useMemo, useRef, useState } from "react"
import Similarity from "./Similarity"
import baseApi from "../../assets/api/baseApi"
import Notification from "../notification/Notification"
import { fetchBook, transactionService } from "../../assets/type/interface"
import { formatMoney } from "../../assets/service/format"
import Cookies from "js-cookie"

export default function Detail() {

  const [turnOnFan, setturnOnFan] = useState<boolean>(false)
  const { id, request } = useParams()
  const navigate = useNavigate()
  const alert = useRef<any>(null)

  const accessToken = Cookies.get("access-auth-token")

  const dfTransaction: transactionService = {
    bookId: Number(id),
    isPurchased: false,
    numberOfBooks: 1
  }

  const [detail, setDetail] = useState<fetchBook>({} as fetchBook)
  const [category, setCategory] = useState<number>(0)

  const [transactions, setTransactions] = useState<transactionService>(dfTransaction)
  useMemo(() => {
    if (request !== "borrowing" && request !== "buy") {
      navigate(-1)
    }

    if (accessToken === undefined || accessToken === null) {
      alert.current?.showAlert("info", "vui lòng đăng nhập để tiếp tục!")
      setTimeout(() => {
        navigate("/auth/login")
      }, 3000)
    }
  }, [request, navigate])

  const getDetail = async () => {
    baseApi.get(`books/${id}`, alert.current).then((res: any) => {
      setDetail(res.data.data)
      setCategory(res.data.data.categoryId)
    })
  }
  
  useMemo(() => {
    getDetail()
  }, [id])

  const createTransaction = () => {
    baseApi.postAccess(`transactions`, { ...transactions, isPurchased: true }, alert.current).then((res: any) => {
      alert.current?.showAlert("success", res.data.message)
      alert.current?.showAlert("success", "đã tạo giao dịch thành công (đơn mua)...")
    })
  }

  const getEffect = () => {
    setturnOnFan(true)

    setTimeout(() => {
      setturnOnFan(false)
    }, 5000)
  }

  return (
    <div>
      <div className={`alert  alert-card ${turnOnFan ? "effect-bunny" : ""}`}>

        <div className="row mt-2 mb-2">
          <div className="col medium">
            <h3 className=" mb-3 title">
              <i className="fa-solid fa-circle-info" onClick={getEffect}></i>{" "}
              Details :
            </h3>
          </div>
          <div className="row  mb-2">
            <div key={detail.id} className="col">
              <div className="alert flex-col-center card-content-2">
                <div className="header-card">
                  {detail.promotionValue > 0 && (
                    <h3 className="mb-3 flex-end tag small">
                      -{detail.promotionValue}%
                    </h3>
                  )}
                  <img
                    src={detail.avatar}
                    alt={detail.name}
                    className="avatar-detail"
                  />
                </div>
                <div className="body-card">
                  <div className="row">
                    <div className="col">
                      <h3 className="mb-3 book-name"> {detail.name}</h3>
                      <h3 className="mb-3">Có sẵn : {detail.quantity}</h3>
                      <h3 className="mb-3"> Danh mục : {detail.categoryName}</h3>
                      <h3 className="mb-3">
                        Lượt giao dịch : {detail.transactionVolume}
                      </h3>
                      {detail.promotionValue > 0 && (
                        <>
                          <h3 className={`remove small tag-3 `}>
                            {formatMoney(detail.price)}{" "}
                          </h3>
                          <h3 className="tag-2 mt-3  ">
                            {" "}
                            {formatMoney(
                              (detail.price * (100 - detail.promotionValue)) /
                              100
                            )}{" "}
                          </h3>
                        </>
                      )}
                      {detail.promotionValue <= 0 && (
                        <h3 className={`small tag-3`}>
                          {formatMoney(detail.price)}{" "}
                        </h3>
                      )}
                    </div>
                  </div>
                </div>
                <div className="footer-card">
                  {detail.quantity > 0 && (
                    <div className="row">
                      <div className="col-md-3 flex-center">
                        <button className="button small" onClick={() => createTransaction()}>
                          <i className="fa-solid fa-cart-plus"></i>
                        </button>
                      </div>
                      <div className="col-md-3 flex-center">
                        <button className="button small">
                          <i className="fa-solid fa-cart-plus"></i>
                        </button>
                      </div>
                      <div className="col-md-3 flex-center">
                        <button className="button small">
                          <i className="fa-solid fa-cart-plus"></i>
                        </button>
                      </div>
                      <div className="col-md-3 flex-center">
                        <button className="button small">
                          <i className="fa-solid fa-heart"></i>
                        </button>
                      </div>
                    </div>
                  )}
                </div>

                {detail.quantity <= 0 && (
                  <div className="alert notice small">
                    <span> Out of stock! </span>
                  </div>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      {detail.categoryId > 0 && <Similarity category={category} thisB={0} />}
      <Notification ref={alert} />
    </div>
  )
}
