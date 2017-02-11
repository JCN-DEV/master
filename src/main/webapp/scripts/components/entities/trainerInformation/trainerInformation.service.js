'use strict';

angular.module('stepApp')
    .factory('TrainerInformation', function ($resource, DateUtils) {
        return $resource('api/trainerInformations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('TrainerListByTrainingCode', function ($resource, DateUtils) {
        return $resource('api/trainerInformations/TrainerListByTrainingCode/:pTrainingCode', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('TrainerListByInitializationId', function ($resource, DateUtils) {
        return $resource('api/trainerInformations/TrainerListByInitializationId/:pInitializationId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
