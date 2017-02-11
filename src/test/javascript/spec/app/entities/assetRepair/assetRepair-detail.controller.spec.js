'use strict';

describe('AssetRepair Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockAssetRepair, MockEmployee, MockAssetRecord;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockAssetRepair = jasmine.createSpy('MockAssetRepair');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockAssetRecord = jasmine.createSpy('MockAssetRecord');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'AssetRepair': MockAssetRepair,
            'Employee': MockEmployee,
            'AssetRecord': MockAssetRecord
        };
        createController = function() {
            $injector.get('$controller')("AssetRepairDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:assetRepairUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
