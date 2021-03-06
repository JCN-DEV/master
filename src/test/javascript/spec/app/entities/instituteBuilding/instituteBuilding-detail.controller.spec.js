'use strict';

describe('InstituteBuilding Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstituteBuilding;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstituteBuilding = jasmine.createSpy('MockInstituteBuilding');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstituteBuilding': MockInstituteBuilding
        };
        createController = function() {
            $injector.get('$controller')("InstituteBuildingDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instituteBuildingUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
