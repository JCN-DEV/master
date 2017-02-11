'use strict';

angular.module('stepApp')
    .factory('TrainingRequisitionApproveAndForward', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionApproveAndForwards/:id', {}, {
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
    }).factory('TrainingRequisitionDecline', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionApproveAndForwards/trainingRequisitionDecline', {}, {
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('TrainingRequisitionReject', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionApproveAndForwards/trainingRequisitionReject', {}, {
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('TrainingRequisitionApproved', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionForms/trainingApprovedList', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ApprovedDataByRequisitionAndApproveStatus', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionApproveAndForwards/findByRequisitionAndApproveStatus/:requisitionId/:approveStatus', {}, {
            'get': { method: 'GET'}
        });
    });
