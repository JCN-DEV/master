'use strict';

angular.module('stepApp')
    .factory('InformationCorrectionSearch', function ($resource) {
        return $resource('api/_search/informationCorrections/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }) .factory('InformationCorrectionsByStatus', function ($resource) {
        return $resource('api/informationCorrections/applications/:status', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InformationCorrectionByEmployeeCode', function ($resource) {
        return $resource('api/informationCorrections/instEmployee/:code', {}, {
            'query': { method: 'GET', isArray: false}
        });
    }).factory('InformationCorrectionDecline', function ($resource) {
        return $resource('api/informationCorrections/decline/:id', {}, {
            'decline': { method: 'PUT', isArray: false}
        });
    });
