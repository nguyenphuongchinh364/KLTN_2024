from flask import Flask, jsonify, request
import mysql.connector

app = Flask(__name__)

# Function to connect to the database
def get_db():
    db_host = "localhost"
    db_user = "root"
    db_password = "123456"
    db_name = "ttcn"
    try:
        connection = mysql.connector.connect(
            host=db_host,
            user=db_user,
            password=db_password,
            database=db_name
        )
        return connection
    except mysql.connector.Error as error:
        print("Error while connecting to MySQL", error)
        return None

# Function to get user-item rating data from the database
def get_user_item_rating_data():
    db = get_db()
    if db is not None:
        cursor = db.cursor()
        try:
            sql = "SELECT userId, productId, reviewsRating FROM reviews WHERE reviewsStatus = 1"
            cursor.execute(sql)
            data = cursor.fetchall()
            return data
        except mysql.connector.Error as error:
            print("Error fetching data from MySQL", error)
        finally:
            cursor.close()
            db.close()
    return None

# Collaborative filtering class
class CF(object):
    def __init__(self, Y_data, k, uuCF=1):
        self.uuCF = uuCF
        if uuCF:
            self.Y_data = [[row[0], row[1], row[2]] for row in Y_data]
        else:
            self.Y_data = [[row[1], row[0], row[2]] for row in Y_data]
        self.k = k
        self.n_users = int(max([row[0] for row in self.Y_data])) + 1
        self.n_items = int(max([row[1] for row in self.Y_data])) + 1

    def add(self, new_data):
        self.Y_data += new_data

    def normalize_Y(self):
        self.Ybar_data = self.Y_data.copy()
        self.mu = [0] * self.n_users
        for n in range(self.n_users):
            ids = [i for i, j in enumerate(self.Y_data) if j[0] == n]
            item_ids = [self.Y_data[i][1] for i in ids]
            ratings = [self.Y_data[i][2] for i in ids]
            m = sum(ratings) / (len(ratings) + 1e-8)
            self.mu[n] = m if not m != m else 0
            for i in range(len(ids)):
                self.Ybar_data[ids[i]][2] -= self.mu[n]

    def similarity(self):
        self.S = [[0] * self.n_users for _ in range(self.n_users)]
        self.S = [[self.cosine_similarity(self.Ybar_data[i], self.Ybar_data[j]) for j in range(self.n_users)] for i in range(self.n_users)]

    def refresh(self):
        self.normalize_Y()
        self.similarity()

    def fit(self):
        self.refresh()

    def cosine_similarity(self, rating1, rating2):
        r1 = [rating1[i] for i in range(len(rating1)) if rating1[i] != 0 and rating2[i] != 0]
        r2 = [rating2[i] for i in range(len(rating2)) if rating1[i] != 0 and rating2[i] != 0]
        if len(r1) == 0 or len(r2) == 0:
            return 0
        return sum([r1[i] * r2[i] for i in range(len(r1))]) / (sum([r1[i] ** 2 for i in range(len(r1))]) ** 0.5 * sum([r2[i] ** 2 for i in range(len(r2))]) ** 0.5 + 1e-8)

    def pred(self, u, i):
        ids = [j for j in range(len(self.Y_data)) if self.Y_data[j][1] == i]
        users_rated_i = [self.Y_data[j][0] for j in ids]
        sim = [self.S[u][j] for j in users_rated_i]
        a = [sorted(sim).index(sim[j]) for j in range(len(sim))][-self.k:]
        nearest_s = [sim[j] for j in a]
        r = [self.Ybar_data[ids[j]][2] for j in range(len(ids))]
        return sum([r[j] * nearest_s[j] for j in range(len(a))]) / (sum([abs(nearest_s[j]) for j in range(len(a))]) + 1e-8)

    def recommend(self, u):
        ids = [i for i in range(len(self.Y_data)) if self.Y_data[i][0] == u]
        items_rated_by_u = [self.Y_data[i][1] for i in ids]
        recommended_items = [i for i in range(self.n_items) if i not in items_rated_by_u]
        recommended_items = [i for i in recommended_items if self.pred(u, i) > 0]
        return recommended_items

# Route for getting recommendations for all users
@app.route('/recommendation/all', methods=['GET'])
def get_recommendations_for_all_users():
    # Get user-item rating data
    Y_data = get_user_item_rating_data()
    if Y_data is None:
        return jsonify({"error": "Failed to fetch user-item rating data"}), 500

    # Initialize and fit CF model
    rs = CF(Y_data, k=2, uuCF=1)
    rs.fit()

    # Dictionary to store recommendations for all users
    all_recommendations = {}

    # Get unique user IDs from the data
    user_ids = set(row[0] for row in Y_data)

    # Generate recommendations for each user
    for userId in user_ids:
        recommended_items = rs.recommend(userId)
        all_recommendations[userId] = recommended_items

    return jsonify(all_recommendations)


# Route for getting recommendations
@app.route('/recommendation', methods=['GET'])
def get_recommendation():
    userId = int(request.args.get('userId'))
    if userId is None:
        return jsonify({"error": "User ID is required"}), 400

    # Get user-item rating data
    Y_data = get_user_item_rating_data()
    if Y_data is None:
        return jsonify({"error": "Failed to fetch user-item rating data"}), 500
    
    

    # Initialize and fit CF model
    rs = CF(Y_data, k=2, uuCF=1)
    rs.fit()

    # Get recommendations for the user
    recommended_items = rs.recommend(userId)

    return jsonify({"userId": userId, "recommended_items": recommended_items})

# Run the Flask app
if __name__ == '__main__':
    app.run(debug=True, port=12345)



