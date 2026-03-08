 LxL — メンズファッション ECサイト

> 「とにかく動くものを作りきる」を目標に、はじめて本格的なWebアプリを作りました。

---

 なぜこれを作ったのか

プログラミングを学ぶ中で「チュートリアルを終わらせるだけ」になっている自分に気づきました。  
実際に動くものを0から作らないと身につかないと思い、自分が好きなファッションをテーマにECサイトを作ることにしました。

単純なCRUDアプリではなく、**会員登録・ログイン・カート・購入という実際のECサイトに近い流れ**を実装することにこだわりました。

---

## 🖥 デモ画面

| トップページ | 商品一覧 |
|---|---|
| ![top](docs/top.png) | ![list](docs/list.png) |

| 商品詳細 | カート |
|---|---|
| ![detail](docs/detail.png) | ![cart](docs/cart.png) |

---

## 🛠 使用技術

### バックエンド
| 技術 | バージョン | 用途 |
|---|---|---|
| Java | 17 | メイン言語 |
| Spring Boot | 4.0.3 | Webフレームワーク |
| JdbcTemplate | - | DB操作（SQL直書き） |
| BCrypt | - | パスワードハッシュ化 |
| HttpSession | - | ログイン状態管理 |

### フロントエンド
| 技術 | 用途 |
|---|---|
| Thymeleaf | テンプレートエンジン |
| HTML / CSS | マークアップ・スタイリング |
| JavaScript | DOM操作（数量変更・タブ切替など） |
| Google Fonts | タイポグラフィ |

### データベース・環境
| 技術 | 用途 |
|---|---|
| MySQL 8.0 | データベース |
| XAMPP | ローカル開発環境 |
| Gradle | ビルドツール |
| Eclipse IDE | 開発エディタ |

---

 実装した機能

- 会員登録 / ログイン / ログアウト
- パスワードのBCryptハッシュ化
- 商品一覧（カテゴリ絞り込み）
- 商品詳細
- カートへの追加 / 削除
- 購入手続き（配送先・支払い方法入力）
- 注文確定 → 購入完了ページ
- ログイン状態によるヘッダー表示切替
- 未ログイン時のアクセス制限（カート・購入はログイン必須）

---

 工夫した点

### デザインにこだわった
ただ機能を作るだけでなく、実際のECサイトに近い見た目にこだわりました。  
カラーパレット・フォント・余白まで設計書を作ってから実装しています。

### Controller / Service / Repository に役割を分けた
最初は1つのクラスに全部書いていましたが、MVC の考え方を学んで役割を分けるようにしました。  
コードが整理されて、どこに何を書くべきかが自然とわかるようになりました。

### SQLを自分で書いた
JPAを使おうとしたところでアノテーションのエラーが解決できず、JdbcTemplateに切り替えました。  
結果的にSQLを直接書くことになり、DB操作の仕組みを深く理解できたのでよかったと思っています。

### セキュリティを意識した
- パスワードはBCryptでハッシュ化して保存
- SQLはプレースホルダー（?）を使ってSQLインジェクションを防止
- 未ログインユーザーがカート・購入ページにアクセスしたらログインページへリダイレクト

---

苦労した点

### 環境構築に予想以上に時間がかかった
MySQLの接続設定やXAMPPの使い方など、コードを書く前の環境構築だけで詰まることが何度もありました。  
エラーメッセージをちゃんと読めば解決できることが多いと気づいてから、だいぶ楽になりました。

### Spring特有の仕組みを理解するのに時間がかかった
`@Autowired` や `@Bean` など、Springが裏でやってくれることが最初はまったく見えませんでした。  
実際に動かして、エラーを出して、直すを繰り返す中でだんだんと理解できました。

### コードを読むことと書くことは別のスキルだった
「読めば理解できる」のに「いざ書こうとすると手が止まる」という壁にぶつかりました。  
見ないで書く練習を意識するようにしてから、少しずつ書けるようになってきました。

---

 DB設計

```
users
├── id
├── last_name / first_name
├── email（UNIQUE）
├── password（BCryptハッシュ）
└── role（USER / ADMIN）

products
├── id
├── name / description
├── price / original_price / sale_price
├── image_url / category / stock
├── is_new / on_sale
└── created_at / updated_at

cart_items
├── id
├── user_id（FK → users）
├── product_id（FK → products）
└── quantity

orders
├── id
├── user_id（FK → users）
├── 配送先情報（last_name〜phone）
├── 金額情報（total_price / tax_amount / shipping_fee / grand_total）
├── payment_method / status
└── created_at

order_items
├── id
├── order_id（FK → orders）
├── product_id / product_name
├── price / quantity
```

---

## 🚀 ローカルでの起動方法

**1. リポジトリをクローン**
```bash
git clone https://github.com/yourname/ecsite-portfolio.git
```

**2. MySQLでDBを作成**
```sql
CREATE DATABASE ecsite_portfolio
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

**3. テーブルを作成**

`src/main/resources/sql/` 内のSQLファイルを順番に実行してください。

**4. application.properties を編集**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecsite_portfolio?useSSL=false&serverTimezone=Asia/Tokyo
spring.datasource.username=root
spring.datasource.password=（自分のパスワード）
```

**5. Spring Boot を起動**

Eclipse で `EcsitePortfolioApplication.java` を右クリック → `Run As` → `Spring Boot App`

**6. ブラウザでアクセス**
```
http://localhost:8080
```

---

## 📝 今後やりたいこと

- [ ] 管理者画面（商品の追加・編集・削除）
- [ ] バリデーション強化（メールアドレス重複・在庫切れ時のメッセージ）
- [ ] AWSへのデプロイ
- [ ] テストコードの追加

---

## 👤 作者

就職活動中の学生です。バックエンド・DB設計を中心に学習中です。  
フィードバックや改善点があればIssueで教えていただけると嬉しいです。

---

*このポートフォリオは就職活動のために作成しました。*
