import {useMemo, useRef, useState } from "react"
import { fetchBook } from "../../assets/type/interface"
import baseApi from "../../assets/api/baseApi"
import Notification from "../notification/Notification"
import { formatMoney } from "../../assets/service/format"
import { Link } from "react-router-dom"

const ModalView = ({ id, onClose }: { id: number; onClose: () => void }) => {
  const alert = useRef<any>(null)

  const [detail, setDetail] = useState<fetchBook>({} as fetchBook)

  const getDetail = (id: number) => {
    baseApi.get(`books/${id}`, alert.current).then((res: any) => {
      setDetail(res.data.data)
    })
  }

  useMemo(() => {
    if (id > 0) {
      getDetail(id)
    }
  }, [id])

  useMemo(() => {
    const handleKeyDown = (event: KeyboardEvent) => {
      if (event.key === "Escape") {
        onClose()
      }
    }

    window.addEventListener("keydown", handleKeyDown)

    return () => {
      window.removeEventListener("keydown", handleKeyDown)
    }
  }, [onClose])

  const handleOutsideClick = (event: React.MouseEvent<HTMLDivElement>) => {
    if ((event.target as HTMLElement).classList.contains("modal-view")) {
      onClose()
    }
  }

  return (
    <div className="modal-view" onClick={handleOutsideClick}>
      <div className="container">
        <div className="alert alert-form">
          <div className="row mt-4 mb-2">
            <div key={id} className="col">
              <div className="alert flex-col-center card-content-2">
                <div className="header-card">
                  {detail.promotionValue > 0 && (
                    <h3 className="mb-3 flex-end tag small">
                      -{detail.promotionValue}%
                    </h3>
                  )}
                  <img
                    src={detail.avatar}
                    alt="avatar"
                    className="avatar-detail"
                  />
                </div>
                <div className="body-card">
                  <div className="row">
                    <div className="col">
                      <h3 className="mb-3 book-name">Name: {detail.name}</h3>
                      <h3 className="mb-3 ">
                        Category: {detail.categoryName || "No category"}{" "}
                      </h3>
                    
                      {detail.promotionValue > 0 && (
                        <>
                          <h3 className={`mt-3 remove small tag-3 `}>
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
                        <h3 className={`mb-3 small tag-3`}>
                          {formatMoney(detail.price)}{" "}
                        </h3>
                      )}
                    </div>
                  </div>
                </div>
                <div className="footer-card">
                  <div className="row">
                    <div className="col-md-3 flex-start">
                      <button
                        className="button small"
                        title="thêm vào giỏ hàng"
                      >
                        <i className="fa-solid fa-cart-plus"></i>
                      </button>
                    </div>
                    <div className="col-md-3 flex-start">
                      <button
                        className="button small"
                        title="Yêu cầu mượn sách"
                      >
                        <Link to={`/detail/` + detail.id + `/borrowing`}>
                          <i className="fa-solid fa-hands"></i>
                        </Link>
                      </button>
                    </div>
                    <div className="col-md-3 flex-start">
                      <button className="button small" title="mua ngay">
                        <Link to={`/detail/` + detail.id + `/buy`}>
                          <i className="fa-solid fa-bag-shopping"></i>
                        </Link>
                      </button>
                    </div>
                    <div className="col-md-3 flex-center">
                      <button
                        className="button small"
                        title="thêm vào ưa thích"
                      >
                        <i className="fa-solid fa-heart"></i>
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div />
          </div>
        </div>
      </div>
      <Notification ref={alert} />
    </div>
  )
}

export default ModalView
