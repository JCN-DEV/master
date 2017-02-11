'use strict';

angular.module('stepApp')
    .factory('TrainingRequisitionForm', function ($resource, DateUtils) {
        return $resource('api/trainingRequisitionForms/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.applyDate = DateUtils.convertLocaleDateFromServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.applyDate = DateUtils.convertLocaleDateToServer(data.applyDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    })

    .factory('TrainingReqDataByReqCode', function ($resource) {
        return $resource('api/trainingRequisitionForms/trainingReqData/:trainingReqcode', {}, {
            'get': { method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                return data;
                }
            }
        });
    })

    .factory('TrainingReqPendingData', function ($resource) {
        return $resource('api/trainingRequisitionForms/trainingReqPending', {}, {
            'query': { method: 'GET', isArray: true}

        });
    })
    .factory('TrainingRequisitionByCurrentUser', function ($resource) {
        return $resource('api/trainingRequisitionForms/trainingReqByCurrentUser', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('TrainingRequisitionByApproveStatus', function ($resource) {
        return $resource('api/trainingRequisitionForms/trainingReqByApproveStatus/:approveStatus', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });

