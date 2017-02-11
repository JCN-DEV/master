'use strict';

angular.module('stepApp')
    .factory('TrainingRequisitionApproveAndForwardSearch', function ($resource) {
        return $resource('api/_search/trainingRequisitionApproveAndForwards/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
