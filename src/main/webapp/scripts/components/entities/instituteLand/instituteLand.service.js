'use strict';

angular.module('stepApp')
    .factory('InstituteLand', function ($resource, DateUtils) {
        return $resource('api/instituteLands/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.landRegistrationDate = DateUtils.convertLocaleDateFromServer(data.landRegistrationDate);
                    data.lastTaxPaymentDate = DateUtils.convertLocaleDateFromServer(data.lastTaxPaymentDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.landRegistrationDate = DateUtils.convertLocaleDateToServer(data.landRegistrationDate);
                    data.lastTaxPaymentDate = DateUtils.convertLocaleDateToServer(data.lastTaxPaymentDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.landRegistrationDate = DateUtils.convertLocaleDateToServer(data.landRegistrationDate);
                    data.lastTaxPaymentDate = DateUtils.convertLocaleDateToServer(data.lastTaxPaymentDate);
                    return angular.toJson(data);
                }
            }
        });
    })
    .factory('InstituteLandByInstitute', function ($resource) {
        return $resource('api/InstituteLandByInstituteId/:id', {}, {
            'get': { method: 'get', isArray: false}
        });
    });
