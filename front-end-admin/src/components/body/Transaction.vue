<template>
  <h3 class="text-center mt-5 mb-5 large" :class="classTable">
    Quản lý giao dịch
    <i
      class="fa-solid fa-credit-card"
      title="Create a transaction"
      v-on:click="isCreate = !isCreate"
    ></i>
    <i
      class="fa-solid ml-3 fa-rocket effect-click"
      v-on:click="activeEffect()"
    ></i>
  </h3>

  <div class="alert alert-form" v-if="isCreate" :class="classForm">
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Tên sách</label>
      <input type="text" class="form-control" placeholder="Điền vào tên sách" />
    </div>
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Chọn danh mục</label>
      <select class="form-control">
        <!-- <option v-bind:value="0">Chưa có danh mục</option>
          <option v-for="(v, k) in dataCategory" :value="v.id" :key="k">
            {{ v.name }}
          </option> -->
      </select>
    </div>
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Số lượng </label>
      <input
        type="number"
        class="form-control"
        placeholder="Điền vào số lượng"
      />
    </div>
    <div class="form-group mt-3">
      <label class="mb-3 mt-3">Gía</label>
      <input
        type="number"
        class="form-control"
        placeholder="Điền vào số lượng"
      />
    </div>
    <div class="row item-right mt-5">
      <button class="btn btn-success mb-3">
        <i class="fa-solid fa-floppy-disk"></i> Create
      </button>
      <button class="btn btn-primary ml-3 mb-3">
        <i class="fa-solid fa-rotate"></i> Clear
      </button>
      <button class="btn btn-warning ml-3 mb-3" v-on:click="isCreate = false">
        <i class="fa-regular fa-circle-xmark"></i> Close
      </button>
    </div>
  </div>

  <div class="row mt-2" :class="classForm">
    <div class="col">
      <div class="alert alert-tabbar">
        <div class="left-tabbar">
          <button class="btn btn-info" v-on:click="pageOption.index--">
            <i class="fa-solid fa-arrow-left-long"></i>
          </button>
          <input
            type="number"
            class="small-input"
            readonly
            v-model="pageOption.index"
          />
          <button class="btn btn-info" v-on:click="pageOption.index++">
            <i class="fa-solid fa-arrow-right-long"></i>
          </button>
        </div>

        <div class="center-tabbar">
          <select class="form-control" v-model="pageOption.size">
            <option v-bind:value="5">Choose Size (default 5)</option>
            <option v-bind:value="10">10</option>
          </select>
        </div>

        <div class="center-tabbar">
          <select class="form-control" v-model="pageOption.sortByCol">
            <option value="id">Sort by column (default id)</option>
            <option value="saleValue">Tỉ lệ khuyến mãi</option>
            <option value="numberOfBooks">Số lượng giao dịch</option>
            <option value="transactionDate">Ngày giao dịch</option>
            <option value="expiredDate">Ngày hết hạn</option>
          </select>
        </div>

        <div class="right-tabbar">
          <select class="form-control" v-model="pageOption.sortOption">
            <option value="desc">Sort option (default desc)</option>
            <option value="asc">asc</option>
          </select>
        </div>
      </div>
    </div>
  </div>

  <table class="table table-striped table-dark mt-2 mb-5" :class="classTable">
    <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Tên Sách</th>
        <th scope="col">Email khách hàng</th>
        <th scope="col">Số Lượng giao dịch</th>
        <th scope="col">Đơn giá</th>
        <th scope="col">Ngày giao dịch</th>
        <th scope="col">Ngày hết hạn</th>
        <th scope="col">Action</th>
      </tr>
    </thead>
    <tbody>
      <tr
        v-if="transactions && transactions.length"
        v-for="(v, k) in transactions"
        :key="k"
      >
        <th scope="row">{{ k + 1 }}</th>
        <td>{{ v.bookName }}</td>
        <td>{{ v.email }}</td>
        <td>{{ v.numberOfBooks }}</td>
        <td>
          {{ formatMoney(v.singlePrice) }}
        </td>
        <td>{{ formatDateTime(v.transactionDate) }}</td>
        <td>
          {{ v.isPurchased ? "Purchased" : formatDateTime(v.expiredDate) }}
        </td>

        <td>
          <button
            class="btn btn-success mb-2"
            v-on:click="selectTransaction(v.id)"
            title="xem chi tiết hoặc update"
          >
            <i class="fas fa-edit"></i>
          </button>
          <button
            class="btn btn-danger mb-2 ml-2"
            title="delete"
            v-on:click="openDeleteTransaction(v.id)"
          >
            <i class="fas fa-trash"></i>
          </button>
          <button
            class="btn btn-primary"
            v-on:click="changeIsPurchased(v.id)"
            v-if="!v.isPurchased"
          >
            Borrowing <i class="fa-solid fa-sack-dollar"></i>
          </button>
          <button
            class="btn btn-success"
            v-on:click="changeIsPurchased(v.id)"
            v-else
          >
            Purchased <i class="fa-solid fa-circle-check"></i>
          </button>
        </td>
      </tr>
      <tr v-else>
        <td colspan="8" class="text-center">
          <i class="fa-solid fa-spinner"></i> No Data Available
        </td>
      </tr>
    </tbody>
  </table>

  <div class="modal">
    <div class="modal-content" v-if="isSelected" :key="selectedTransaction.id">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">
          Chi tiết
          <button v-on:click="isUpdate = !isUpdate" class="btn btn-info ml-2">
            <i class="fa-solid fa-pen"></i> Edit
          </button>
        </h5>
      </div>

      <div class="modal-body">
        <p class="small">
          Loại giao dịch :
          <span class="mr-2">
            {{
              selectedTransaction.isPurchased ? "Purchased" : "Not Purchased"
            }}
            <i
              :class="
                selectedTransaction.isPurchased
                  ? 'fa-solid fa-file-circle-check'
                  : 'fa-solid fa-filter-circle-xmark'
              "
            ></i>
          </span>
        </p>

        <p class="mt-3 line-vote">
          Email khách hàng :
          <input
            v-if="isUpdate"
            type="email"
            v-model="selectedTransaction.email"
            class="form-control mt-2"
          />

          <span v-else>
            {{ selectedTransaction.email }}
          </span>
        </p>

        <p class="mt-3 line-vote">
          Tên sách :
          {{ selectedTransaction.bookName }}
        </p>

        <p class="mt-3">
          Tổng tiền :
          {{ formatMoney(selectedTransaction.singlePrice) }}
        </p>

        <p class="mt-3">
          Số lượng giao dịch :
          <input
            v-if="isUpdate"
            type="number"
            v-model="selectedTransaction.numberOfBooks"
            class="form-control mt-2"
          />

          <span v-else> {{ selectedTransaction.numberOfBooks }} </span>
        </p>

        <p class="mt-3">
          Voucher :
          <input
            v-if="isUpdate && selectedTransaction.isPurchased"
            type="number"
            v-model="selectedTransaction.saleValue"
            class="form-control mt-2"
            @input="validateSaleValue"
          />

          <span v-else> {{ selectedTransaction.saleValue }} % </span>
        </p>

        <p class="mt-3">
          Ngày giao dịch :
          <input
            v-if="isUpdate"
            type="datetime-local"
            v-model="selectedTransaction.transactionDate"
            class="form-control mt-2"
          />

          <span v-else>
            {{ formatDateTime(selectedTransaction.transactionDate) }}
          </span>
        </p>

        <p class="mt-3">
          Ngày hết hạn :

          <input
            v-if="isUpdate && !selectedTransaction.isPurchased"
            type="datetime-local"
            v-model="selectedTransaction.expiredDate"
            class="form-control mt-2"
          />

          <span v-else>
            {{
              selectedTransaction.isPurchased
                ? "Purchased"
                : formatDateTime(selectedTransaction.expiredDate)
            }}
          </span>
        </p>

        <p class="mt-3" v-if="isUpdate">
          Tình trạng :
          <select
            v-if="isUpdate && !selectedTransaction.isPurchased"
            v-model="selectedTransaction.status"
            class="form-control mt-2"
          >
            <option value="PENDING">Chờ giải quyết</option>
            <option value="BORROWED">Đang mượn</option>
            <option value="RETURNED">Đã trả</option>
          </select>

          <span v-else>
            {{
              selectedTransaction.isPurchased
                ? "Purchased"
                : selectedTransaction.status === "PENDING"
                ? "Chờ giải quyết"
                : selectedTransaction.status === "BORROWED"
                ? "Đã Mượn"
                : selectedTransaction.status === "RETURNED"
                ? "Đã trả"
                : ""
            }}
          </span>
        </p>
      </div>

      <div class="modal-footer" v-if="isUpdate">
        <button
          class="btn btn-success mt-3"
          v-on:click="updateTransaction(selectedTransaction.id)"
        >
          <i class="fa-solid fa-floppy-disk"></i> Update
        </button>

        <button
          class="btn btn-warning mt-3"
          v-on:click="cancelEdit(selectedTransaction.id)"
        >
          <i class="fa-solid fa-floppy-disk"></i> Cancel
        </button>
      </div>
    </div>

    <div class="modal-content" v-if="isDelete">
      <i class="fas fa-xmark close" v-on:click="closeModal()"></i>
      <div class="modal-header">
        <h5 class="modal-title">Xóa giao dịch {{ selectedTransaction.id }}</h5>
      </div>
      <div class="modal-footer">
        <button class="btn btn-danger mt-3" v-on:click="deleteTransaction(selectedTransaction.id)">
          <i class="fa-solid fa-trash"></i> Accpect Delete
        </button>
      </div>
    </div>
  </div>
  <Notification ref="notificationRef"></Notification>
</template>

<script lang="ts">
import { TransactionData, Pagination } from "../../assets/type/interface"
import Notification from "../notification/Notification.vue"
import baseAPI from "../../assets/api/baseAPI"
import { formatMoney, formatDateTime } from "../../assets/service/format"
export default {
  components: { Notification },
  data(): {
    pageOption: Pagination
    alert: any
    isUseEffect: boolean
    classTable: string
    classForm: string
    isCreate: boolean
    transactions: TransactionData[] | null
    selectedTransaction: TransactionData
    isSelected: boolean
    isOpenBook: boolean
    isOpenCustomer: boolean
    isUpdate: boolean
    isDelete : boolean
  } {
    return {
      isDelete: false,
      pageOption: {
        size: 5,
        index: 1,
        sortByCol: "id",
        sortOption: "desc",
      },
      isUpdate: false,
      isOpenBook: false,
      isOpenCustomer: false,
      isSelected: false,
      alert: null,
      classTable: "",
      classForm: "",
      isUseEffect: false,
      isCreate: false,
      transactions: [],
      selectedTransaction: {
        id: 0,
        saleValue: 0,
        email: "",
        bookId: 0,
        bookName: "",
        singlePrice: 0,
        numberOfBooks: 0,
        transactionDate: "",
        isPurchased: false,
        expiredDate: "",
        status: "",
      },
    }
  },
  created() {
    this.fetchTransactions()
  },

  mounted() {
    this.alert = this.$refs.notificationRef
  },

  watch: {
    pageOption: {
      deep: true,
      handler() {
        this.waitingUpdate()
      },
    },
  },

  methods: {
    formatDateTime: formatDateTime,
    formatMoney: formatMoney,

    waitingUpdate(): void {
      setTimeout(() => {
        this.fetchTransactions()
      }, 500)
    },

    validateSaleValue() {
      if (this.selectedTransaction.saleValue < 0) {
        this.alert.showAlert("warning", "Gía trị khuyến mãi ko được âm!")
        this.selectedTransaction.saleValue = 0
      } else if (this.selectedTransaction.saleValue > 100) {
        this.alert.showAlert("warning", "Gía trị khuyến mãi ko > 100%!")
        this.selectedTransaction.saleValue = 100
      }
    },

    closeModal(): void {
      this.isSelected = false
      this.isDelete = false
      this.isUpdate = false
      this.selectedTransaction = {
        id: 0,
        saleValue: 0,
        email: "",
        bookId: 0,
        bookName: "",
        singlePrice: 0,
        numberOfBooks: 0,
        transactionDate: "",
        isPurchased: false,
        expiredDate: "",
        status: "",
      }
    },
    activeEffect(): void {
      this.isUseEffect = true
      this.classTable = "effect-fly-circle"
      this.classForm = "effect-fly-up"

      setTimeout(() => {
        this.isUseEffect = false
        this.classTable = ""
        this.classForm = ""
      }, 20000)
    },
    fetchTransactions(): void {
      if (this.pageOption.index < 1) {
        this.alert.showAlert("warning", "index cannot be less than 1")
        return
      }

      baseAPI
        .getByAttribute(`transactions`, this.pageOption, this.alert)
        .then((res: any) => {
          this.transactions = res.data.data
        })
    },

    selectTransaction(id: number): void {
      baseAPI.get(`transactions/${id}`, this.alert).then((res: any) => {
        this.selectedTransaction = res.data.data
        this.isSelected = true
      })
    },

    cancelEdit(id: number): void {
      this.isUpdate = false
      if (!this.isUpdate) this.selectTransaction(id)
    },

    updateTransaction(id: number): void {
      baseAPI
        .put(`transactions/${id}`, this.selectedTransaction, this.alert)
        .then((res: any) => {
          this.alert.showAlert("success", res.data.message)
          this.isUpdate = false
          this.waitingUpdate()
        })
    },

    openDeleteTransaction(id: number): void {
      this.isDelete = true
      this.selectedTransaction.id = id
    },

    deleteTransaction(id: number): void {
      baseAPI.delete(`transactions/${id}`, this.alert).then((res: any) => {
        this.alert.showAlert("success", res.data.message)
        this.waitingUpdate()
        this.isDelete = false
      })
    },

    changeIsPurchased(id: number): void {
      baseAPI
        .get(`transactions/update-is-purchase/${id}`, this.alert)
        .then((res: any) => {
          this.alert.showAlert("success", res.data.message)
          this.waitingUpdate()
        })
    },
  },
}
</script>
