'use strict';

angular.module('stepApp')
    .factory('InformationCorrectionStatusLogSearch', function ($resource) {
        return $resource('api/_search/informationCorrectionStatusLogs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('InfoCorrectLogsByEmpCode', function ($resource) {
        return $resource('api/informationCorrectionStatusLogs/instEmployee/:code', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
