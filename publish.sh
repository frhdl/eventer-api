#!/bin/bash
#
# publish.sh - Publish Web Service.
#
# 
#
# -------------------------------------------------------------------------------

# Declaration of some important variables.
FILE_DEPLOYMENT="./env/prod/kubernetes/deployment.yaml"
EVENTER_DEPLOYMENT="./env/prod/kubernetes/deployment.eventer.yaml"
KUBER_CONTEXT="your_context"
KUBER_NAMESPACE="your_namespace"
YOUR_REGISTRY="registry/namespace"
TAG=0.0

#Set docker image name and tag.
IMAGE_NAME_TAG="${YOUR_REGISTRY}/eventer-api:${TAG}"

#Build docker image.
docker build -f "./env/prod/containers/Dockerfile" -t ${IMAGE_NAME_TAG} .

#Tag Image.
docker tag ${IMAGE_NAME_TAG} ${IMAGE_NAME_TAG}

echo "Uploading docker image ${IMAGE_NAME_TAG}..."
docker push ${IMAGE_NAME_TAG}

#Read kubernetes deployment file and replace the image name.
sed -i "s/image_name*/${IMAGE_NAME_TAG}/g" ${FILE_DEPLOYMENT}

#Replace namespace too.
sed -i "s/your_name_space*/${KUBER_NAMESPACE}/g" ${FILE_DEPLOYMENT}
sed -i "s/your_name_space*/${KUBER_NAMESPACE}/g" ${EVENTER_DEPLOYMENT}

# Setup Kubernetes context.
kubectl config use-context ${KUBER_CONTEXT}

kubectl apply -f ${FILE_DEPLOYMENT}

kubectl apply -f ${EVENTER_DEPLOYMENT}

