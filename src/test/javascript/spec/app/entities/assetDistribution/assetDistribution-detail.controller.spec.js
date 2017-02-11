'use strict';

describe('AssetDistribution Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetDistribution, MockEmployee, MockAssetRecord;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetDistribution = jasmine.createSpy('MockAssetDistribution');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockAssetRecord = jasmine.createSpy('MockAssetRecord');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetDistribution': MockAssetDistribution,
            'Employee': MockEmployee,
            'AssetRecord': MockAssetRecord
        };
        createController = function() {
            $injector.get('$controller')("AssetDistributionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetDistributionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
