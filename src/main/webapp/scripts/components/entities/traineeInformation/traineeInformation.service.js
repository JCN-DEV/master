'use strict';

angular.module('stepApp')
    .factory('TraineeInformation', function ($resource, DateUtils) {
        return $resource('api/traineeInformations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.address = DateUtils.convertLocaleDateFromServer(data.address);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.address = DateUtils.convertLocaleDateToServer(data.address);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.address = DateUtils.convertLocaleDateToServer(data.address);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    //.factory('TraineeList', function ($resource) {
    //    return $resource('api/traineeInformations/traineeList/:id', {}, {
    //        'query': { method: 'GET', isArray: true}
    //    });
    //})

    .factory('TraineeListByTrainingCode', function ($resource) {
        return $resource('api/traineeInformations/traineeListByTrainingCode/:pTrainingCode', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('TraineeListByRequisition', function ($resource) {
        return $resource('api/traineeInformations/traineeListByRequisition/:pTrainingReqId', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
