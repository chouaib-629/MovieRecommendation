# Movie Recommendation System

This project implements a Hadoop-based Movie Recommendation System using the [MovieLens](https://grouplens.org/datasets/movielens/) dataset. It utilizes MapReduce for processing data and applying algorithms for generating movie recommendations based on user ratings. The data processing pipeline sorts ratings data in descending order to assist with the recommendation process.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [Contact Information](#contact-information)

## Features

- MapReduce implementation to process large-scale data
- Movie recommendation system based on user ratings
- Utilizes Hadoop for distributed processing
- Scalable to handle large datasets

## Technologies Used

- **Hadoop**: A framework for distributed storage and processing of big data.
- **Java**: The primary programming language used for writing the MapReduce logic.
- **HDFS**: The Hadoop Distributed File System for storing large datasets.
- **MapReduce**: The processing model used to process data.
- **Linux (Ubuntu)**: Operating System.

## Dataset structure and details

The dataset used in this project is the [MovieLens](https://grouplens.org/datasets/movielens/) dataset (version [32M](https://files.grouplens.org/datasets/movielens/ml-32m.zip)). It consists of several CSV files, including `ratings.csv`, which is the main file processed by this MapReduce job.

**Sample of `ratings.csv`**

| userId | movieId | rating | timestamp |
|--------|---------|--------|-----------|
| 1      | 17      | 4.0    | 944249077 |
| 1      | 25      | 1.0    | 944250228 |
| 1      | 29      | 2.0    | 943230976 |
| 1      | 30      | 5.0    | 944249077 |
| 1      | 32      | 5.0    | 943228858 |

## Getting Started

To get started with this project, follow the steps below.

### Prerequisites

1. Install Hadoop on your local machine or use a cloud-based Hadoop cluster.
2. Ensure that Java (JDK 8 or later) is installed on your system.
3. Download the MovieLens dataset (`ml-32m`) from [MovieLens](https://grouplens.org/datasets/movielens/).

### Setup Instructions

1. Clone this repository:

   ```bash
   git clone https://github.com/chouaib-629/MovieRecommendation.git
   ```

2. Navigate to the project directory:

    ```bash
    cd MovieRecommendation
    ```

3. Compile the Java Classes:

    ```bash
    javac -classpath `hadoop classpath` -d compiled_classes src/*.java
    ```

4. Package you classes into a JAR file:

    ```bash
    jar cf movieRating.jar -C compiled_classes/ .
    ```

5. Copy the `ratings.csv` file from the [MovieLens](https://grouplens.org/datasets/movielens/) dataset into the working folder:

    Ensure you have copied the `ratings.csv` file into the working folder before running the job.

6. Create a folder in HDFS called `movies`:

    Before uploading the dataset, create a folder in HDFS to store the data.

    ```bash
    hdfs dfs -mkdir /movies
    ```

7. Copy the `ratings.csv` file to HDFS:

    ```bash
    hdfs dfs -put ratings.csv /movies/ratings.csv
    ```

## Usage

Run the Hadoop job using the following command:

```bash
hadoop jar movieRating.jar Driver /movies/ratings.csv /movies/tmpOutput /movies/finalOutput
```

To view the results of the MapReduce job, use the following command to visualize the output:

```bash
hdfs dfs -cat /movies/finalOutput/part-r-00000
```

**Optional:** Copy the part-r-00000 from HDFS to Local Storage.

After the job has completed, you can copy the resulting `part-r-00000` file from HDFS to your local storage for further processing or analysis. Use the following command:

```bash
hdfs dfs -get /movies/finalOutput/part-r-00000 output/result.csv
```

This will copy the `part-r-00000` file to the `output/result.csv` file in your local storage.

## Contributing

Contributions are welcome! To contribute:

1. Fork the repository.

2. Create a new branch:

   ```bash
   git checkout -b feature/feature-name
   ```

3. Commit your changes:

   ```bash
   git commit -m "Add feature description"
   ```

4. Push to the branch:

   ```bash
   git push origin feature/feature-name
   ```

5. Open a pull request.

## Contact Information

For questions or support, please contact [Me](mailto:chouaiba629@gmail.com).
