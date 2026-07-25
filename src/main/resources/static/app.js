/*** Ride Or Die - Frontend Interakciók & Kosár Kezelés */

document.addEventListener('DOMContentLoaded', () => {
    updateCartBadge();
    initAddToCartButtons();
    initCartPage();
});

// 1. Kosár számláló frissítése a fejlécben
function updateCartBadge() {
    const cart = JSON.parse(localStorage.getItem('cart')) || [];
    const totalCount = cart.reduce((sum, item) => sum + item.quantity, 0);

    // Kezeli a 'cartBadge' és 'cart-count' ID-t is, hogy biztosan működjön!
    const cartBadge = document.getElementById('cartBadge') || document.getElementById('cart-count');
    if (cartBadge) {
        cartBadge.textContent = totalCount;
    }
}

// 2. "Kosárba" gombok eseménykezelője
function initAddToCartButtons() {
    // Elkapja a .btn-red és a .btn-add-to-cart gombokat is!
    const buttons = document.querySelectorAll('.btn-red, .btn-add-to-cart');

    buttons.forEach(button => {
        // Csak a kosárba gombokra rakunk eseményt (a rendelés leadás gombra nem)
        if (button.textContent.toLowerCase().includes('kosárba')) {
            button.addEventListener('click', (e) => {
                e.preventDefault();

                // Megkeressük a kártyát, amiben a gomb van
                const card = button.closest('.card');
                if (!card) return;

                const name = card.querySelector('.card-title')?.textContent.trim() || 'Termék';
                const priceText = card.querySelector('.price-tag')?.textContent.trim() || '0 Ft';
                const price = parseInt(priceText.replace(/[^0-9]/g, '')) || 0;

                addToCart({ name, price });

                // Visszajelzés a usereknek (villan egyet a gomb)
                const originalText = button.innerHTML;
                button.innerHTML = '<i class="bi bi-check2"></i> Betéve!';
                button.classList.add('btn-success');
                setTimeout(() => {
                    button.innerHTML = originalText;
                    button.classList.remove('btn-success');
                }, 1200);
            });
        }
    });
}

// 3. Termék hozzáadása a LocalStorage-ban lévő kosárhoz
function addToCart(product) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];

    const existingIndex = cart.findIndex(item => item.name === product.name);
    if (existingIndex > -1) {
        cart[existingIndex].quantity += 1;
    } else {
        cart.push({ ...product, quantity: 1 });
    }

    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartBadge();
}

// 4. A Kosár oldal (kosar.html) kirajzolása és kezelése
function initCartPage() {
    const cartItemsContainer = document.getElementById('cart-items');
    const cartTotalContainer = document.getElementById('cart-total');

    if (!cartItemsContainer) return; // Ha nem a kosár oldalon vagyunk, leáll

    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cartItemsContainer.innerHTML = '';

    if (cart.length === 0) {
        cartItemsContainer.innerHTML = '<tr><td colspan="4" class="text-center py-4 text-secondary">A kosarad jelenleg üres.</td></tr>';
        if (cartTotalContainer) cartTotalContainer.textContent = '0 Ft';
        return;
    }

    let grandTotal = 0;

    cart.forEach((item, index) => {
        const itemTotal = item.price * item.quantity;
        grandTotal += itemTotal;

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${item.name}</td>
            <td class="price-tag">${item.price.toLocaleString('hu-HU')} Ft</td>
            <td>${item.quantity} db</td>
            <td>
                <button class="btn btn-sm btn-outline-danger remove-btn" data-index="${index}">
                    <i class="bi bi-trash"></i>
                </button>
            </td>
        `;
        cartItemsContainer.appendChild(row);
    });

    if (cartTotalContainer) {
        cartTotalContainer.textContent = grandTotal.toLocaleString('hu-HU') + ' Ft';
    }

    // Törlés gombok bekötése
    document.querySelectorAll('.remove-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const index = e.currentTarget.getAttribute('data-index');
            removeFromCart(index);
        });
    });
}

// 5. Elem törlése a kosárból
function removeFromCart(index) {
    let cart = JSON.parse(localStorage.getItem('cart')) || [];
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    updateCartBadge();
    initCartPage();
}