'use strict';

angular.module('stepApp')
    .factory('TrainingInitializationInfo', function ($resource, DateUtils) {
        return $resource('api/trainingInitializationInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.initializationDate = DateUtils.convertLocaleDateFromServer(data.initializationDate);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.initializationDate = DateUtils.convertLocaleDateToServer(data.initializationDate);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.initializationDate = DateUtils.convertLocaleDateToServer(data.initializationDate);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('TrainingInitializeDataByTrainingCode', function ($resource) {
        return $resource('api/trainingInitializationInfos/getByTrainingCode/:pTrainingCode', {}, {
            'query': { method: 'GET', isArray: false}

        });
    });

