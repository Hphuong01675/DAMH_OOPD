<%@ page contentType="text/html; charset=UTF-8" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

			<!DOCTYPE html>
			<html lang="vi">

			<head>
				<meta charset="UTF-8">
				<title>Customize Drink</title>

				<style>
					body {
						font-family: Segoe UI;
						background: #f5f5f5;
						margin: 0;
					}

					.modal {
						display: flex;
						justify-content: center;
						align-items: center;
						min-height: 100vh;
					}

					.product-detail {
						width: 600px;
						background: white;
						border-radius: 12px;
						box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
						overflow: hidden;
					}

					.header {
						background: linear-gradient(135deg, #f5e6d3, #e8d4c0);
						text-align: center;
						font-size: 60px;
						padding: 30px;
					}

					.body {
						padding: 25px;
					}

					.title {
						font-size: 24px;
						font-weight: 600;
						margin-bottom: 10px;
					}

					.section {
						margin-bottom: 25px;
					}

					.section label {
						font-weight: 600;
						display: block;
						margin-bottom: 10px;
					}

					.quantity {
						display: flex;
						gap: 10px;
						align-items: center;
					}

					.quantity button {
						width: 35px;
						height: 35px;
						font-size: 18px;
						cursor: pointer;
					}

					.size-options {
						display: flex;
						gap: 10px;
					}

					.size {
						flex: 1;
						border: 2px solid #ddd;
						padding: 10px;
						text-align: center;
						cursor: pointer;
						border-radius: 6px;
					}

					.size.active {
						background: #a74880;
						color: white;
						border-color: #a74880;
					}

					.toppings-grid {
						display: grid;
						grid-template-columns: repeat(2, 1fr);
						gap: 15px;
					}

					.topping {
						border: 2px solid #ddd;
						border-radius: 10px;
						padding: 15px;
						text-align: center;
						cursor: pointer;
						transition: 0.3s;
					}

					.topping:hover {
						border-color: #a74880;
					}

					.topping.selected {
						background: #a74880;
						color: white;
						border-color: #a74880;
					}

					.topping-qty {
						display: flex;
						justify-content: center;
						gap: 8px;
						margin-top: 8px;
					}

					.total {
						font-size: 20px;
						font-weight: bold;
						margin-top: 20px;
					}

					.btn {
						width: 100%;
						padding: 12px;
						margin-top: 10px;
						border: none;
						border-radius: 6px;
						font-size: 16px;
						cursor: pointer;
					}

					.add {
						background: #a74880;
						color: white;
					}

					.cancel {
						background: #ddd;
					}
				</style>
			</head>


			<body>

				<div class="modal">

					<div class="product-detail">

						<div class="header">
							🧋
						</div>

						<div class="body">

							<form action="${pageContext.request.contextPath}/buildDrink" method="post">

								<input type="hidden" name="productID" value="${param.productID}">

								<div class="title">
									Customize Drink
								</div>


								<!-- QUANTITY -->
								<div class="section">

									<label>Quantity</label>

									<div class="quantity">

										<button type="button" onclick="changeQty(-1)">-</button>

										<span id="qtyDisplay">1</span>

										<button type="button" onclick="changeQty(1)">+</button>

										<input type="hidden" name="quantity" id="qtyInput" value="1">

									</div>

								</div>


								<!-- SIZE -->
								<div class="section">

									<label>Size</label>

									<div class="size-options">

									<div class="size active" onclick="selectSize(this,'S',0)">
									    S (+0)
									</div>

									<div class="size" onclick="selectSize(this,'M',5000)">
									    M (+5)
									</div>

									<div class="size" onclick="selectSize(this,'L',8000)">
									    L (+8)
									</div>

									<input type="hidden" name="size" id="sizeInput" value="S">
									<input type="hidden" id="sizePrice" value="0">

									</div>

								</div>
								<div class="section">
								<label>Sugar Level</label>

								<select name="sugar" style="width:100%; padding:10px;">
								    <option value="S0">0%</option>
								    <option value="S25">25%</option>
								    <option value="S50" selected>50%</option>
								    <option value="S75">75%</option>
								    <option value="S100">100%</option>
								</select>

								</div>
								<div class="section">
								<label>Ice Level</label>

								<select name="ice" style="width:100%; padding:10px;">
								    <option value="No">No Ice</option>
								    <option value="Les">Less Ice</option>
								    <option value="Normal" selected>Normal</option>
								    <option value="EXTRA">Extra</option>
								</select>

								</div>


								<!-- TOPPING -->
								<div class="section">

									<label>Add Toppings</label>

									<div class="toppings-grid">

										<c:forEach items="${toppings}" var="t">

											<div class="topping" data-name="${t.name}" data-price="${t.price}"
												onclick="toggleTopping(this)">

												<div style="font-size:30px;">🫧</div>

												<div>${t.name}</div>

												<div>+$
													<fmt:formatNumber value="${t.price}" pattern="0.00" />
												</div>

												<input type="hidden" name="toppingNames" value="${t.name}" disabled>
<<<<<<< HEAD
												<input type="hidden" name="toppingQtys" value="1" class="toppingQtyInput" disabled>
=======

>>>>>>> origin/Receive_Notification
												<div class="topping-qty">

													<button type="button"
														onclick="event.stopPropagation(); changeToppingQty(this,-1)">-</button>

													<span class="qty">1</span>

													<button type="button"
														onclick="event.stopPropagation(); changeToppingQty(this,1)">+</button>

												</div>

											</div>

										</c:forEach>

									</div>

								</div>


								<div class="total">
									Total: <span id="totalPrice">0.00 VND</span>
								</div>


								<button class="btn add">
									Add To Cart
								</button>

								<button type="button" class="btn cancel" onclick="history.back()">
									Cancel
								</button>

							</form>

						</div>
					</div>
				</div>



				<script>

					let basePrice = ${basePrice};
					let quantity = 1;

					function changeQty(c) {

						quantity += c;

						if (quantity < 1) quantity = 1;

						document.getElementById("qtyDisplay").innerText = quantity;
						document.getElementById("qtyInput").value = quantity;

						updatePrice();
					}


					function selectSize(el, size, price) {

					    document.querySelectorAll(".size").forEach(s => s.classList.remove("active"));

					    el.classList.add("active");

					    document.getElementById("sizeInput").value = size;

					    document.getElementById("sizePrice").value = price; // ✅ đúng

					    updatePrice();
					}

					function toggleTopping(el) {
<<<<<<< HEAD

					    el.classList.toggle("selected");

					    let nameInput = el.querySelector("input[name='toppingNames']");
					    let qtyInput = el.querySelector(".toppingQtyInput");

					    if (el.classList.contains("selected")) {
					        nameInput.disabled = false;
					        qtyInput.disabled = false; // ✅ thêm dòng này
					    } else {
					        nameInput.disabled = true;
					        qtyInput.disabled = true;
=======
					    el.classList.toggle("selected");

					    let input = el.querySelector("input");

					    if (el.classList.contains("selected")) {
					        input.disabled = false;
					    } else {
					        input.disabled = true;
>>>>>>> origin/Receive_Notification
					    }

					    updatePrice();
					}



					function changeToppingQty(btn, c) {

<<<<<<< HEAD
					    let container = btn.closest(".topping");

					    let qtyEl = container.querySelector(".qty");
					    let inputQty = container.querySelector(".toppingQtyInput");

					    let qty = parseInt(qtyEl.innerText);

					    qty += c;
					    if (qty < 1) qty = 1;

					    qtyEl.innerText = qty;
					    inputQty.value = qty; // ✅ cập nhật hidden input

					    updatePrice();
					}
					
=======
						let qtyEl = btn.parentElement.querySelector(".qty");

						let qty = parseInt(qtyEl.innerText);

						qty += c;

						if (qty < 1) qty = 1;

						qtyEl.innerText = qty;

						updatePrice();
					}
>>>>>>> origin/Receive_Notification
					function formatVND(number) {
					    return number.toLocaleString('vi-VN') + " VND";
					}


					function updatePrice() {

					    let total = basePrice;

					    // size
					    let sizePrice = parseFloat(document.getElementById("sizePrice").value || 0);
					    total += sizePrice;

					    // topping
					    document.querySelectorAll(".topping.selected").forEach(t => {

					        let price = parseFloat(t.dataset.price);
					        let qty = parseInt(t.querySelector(".qty").innerText);

					        total += price * qty;
					    });

					    // quantity
					    total *= quantity;

					    document.getElementById("totalPrice").innerText = formatVND(total);
						document.getElementById("finalPrice").value = total;
					}
					window.onload = function () {
					        updatePrice();
					    };
				</script>


			</body>

			</html>