'use strict';

angular.module('stepApp')
    .factory('HrEmploymentInfo', function ($resource, DateUtils) {
        return $resource('api/hrEmploymentInfos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.joiningDate = DateUtils.convertLocaleDateFromServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateFromServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateFromServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateFromServer(data.officeOrderDate);
                    data.prlDate = DateUtils.convertLocaleDateFromServer(data.prlDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateToServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateToServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.prlDate = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.joiningDate = DateUtils.convertLocaleDateToServer(data.joiningDate);
                    data.regularizationDate = DateUtils.convertLocaleDateToServer(data.regularizationDate);
                    data.confirmationDate = DateUtils.convertLocaleDateToServer(data.confirmationDate);
                    data.officeOrderDate = DateUtils.convertLocaleDateToServer(data.officeOrderDate);
                    data.prlDate = DateUtils.convertLocaleDateToServer(data.prlDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEmploymentInfoApprover', function ($resource) {
        return $resource('api/hrEmploymentInfosApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEmploymentInfoApproverLog', function ($resource) {
        return $resource('api/hrEmploymentInfosApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
