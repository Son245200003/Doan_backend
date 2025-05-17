FROM apache/superset:latest

USER root
# Thêm gcc vào lệnh apt-get install
RUN apt-get update && apt-get install -y \
    gcc \
    libmariadb-dev-compat \
    libmariadb-dev \
    pkg-config \
    && pip install mysqlclient

USER superset
