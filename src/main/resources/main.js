const togglers = document.getElementsByClassName("caret");

for (let toggler of togglers) {
    toggler.addEventListener("click", function(e) {
        this.parentElement.querySelector(".nested").classList.toggle("active");
        this.classList.toggle("caret-down");
        e.stopPropagation();
    });
}