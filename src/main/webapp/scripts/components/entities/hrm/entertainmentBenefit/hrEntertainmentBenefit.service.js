'use strict';

angular.module('stepApp')
    .factory('HrEntertainmentBenefit', function ($resource, DateUtils) {
        return $resource('api/hrEntertainmentBenefits/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.eligibilityDate = DateUtils.convertLocaleDateFromServer(data.eligibilityDate);
                    data.createDate = DateUtils.convertLocaleDateFromServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateFromServer(data.updateDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.eligibilityDate = DateUtils.convertLocaleDateToServer(data.eligibilityDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.eligibilityDate = DateUtils.convertLocaleDateToServer(data.eligibilityDate);
                    data.createDate = DateUtils.convertLocaleDateToServer(data.createDate);
                    data.updateDate = DateUtils.convertLocaleDateToServer(data.updateDate);
                    return angular.toJson(data);
                }
            }
        });
    }).factory('HrEntertainmentBenefitApprover', function ($resource) {
        return $resource('api/hrEntertainmentBenefitsApprover/:id', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    }).factory('HrEntertainmentBenefitApproverLog', function ($resource) {
        return $resource('api/hrEntertainmentBenefitsApprover/log/:entityId', {},
            {
                'update': { method: 'POST'},
                'query': { method: 'GET', isArray: true}
            });
    });
