<template>
  <footer class="footer py-5">
    <div class="container text-center">
      <p>&copy; 2024 Bookpulse Website. All Rights Reserved.</p>
      <div class="social-links">
        <a href="https://facebook.com" target="_blank">
          <i class="fa fa-facebook"></i>
        </a>
        <a href="https://twitter.com" target="_blank">
          <i class="fa fa-twitter"></i>
        </a>
        <a href="https://instagram.com" target="_blank">
          <i class="fa fa-instagram"></i>
        </a>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  name: 'Footer'
}
</script>

<style scoped>
.footer {
  background-color: #f8f9fa;
  color: #6c757d;
}

.footer .social-links a {
  margin: 0 10px;
  color: #6c757d;
  font-size: 20px;
  transition: color 0.3s;
}

.footer .social-links a:hover {
  color: #000;
}
</style>
